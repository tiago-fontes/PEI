# This file is responsible for handle with
# event classification and anomaly detection
# AlertAI class is going to receive the collected data,
# collect the features and procedure to classification
# It is also the sender of events to backend

import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import StandardScaler
import pickle
import requests
import traceback

import jobs
from rqWorker import Workers
workers = Workers()

SUP_MODEL = 'alertai/models/svm_gridsearch.sav'
#UNSUP_MODEL = 'alertai/models/isolationforest.sav'
DB_HOST = 'http://35.189.102.211/api/backend/alert'

anomalies = {
        0: "normal",
        1: "smoke",
        #2: "fire",
    }

class AlertAI:

    def __init__(self):

        self.sensorsData = None
        self.readingsLog= []
        self.start()

    def start(self):
        #Iniciar modelos e definir configs
        self.sup_model = pickle.load(open(SUP_MODEL, 'rb'))
        #self.unsup_model = pickle.load(open(UNSUP_MODEL, 'rb'))
        print("Start went good")
        

    def update(self, dataDict):
        self.sensorsData = dataDict
        self.readingsLog.append(dataDict)
        self.readingsLog = self.readingsLog[:10]#limita a 10 últimos logs p.e. para não crescer infinitamente
        self.processData()

    def processData(self):
        # We must handle with unnecessary columns
        
        raw_data = self.sensorsData.copy()
        #print(raw_data)
        #raw_data.pop('sensors.id')
        raw_data.pop('carId')
        raw_data.pop('carLocation')
        raw_data.pop('timeValue')
        #Altitude must be ignred due to misconfiguration
        raw_data.pop('altitude')
        columns = ['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure']
        dataframe = pd.DataFrame([raw_data])
        if(dataframe['pm25'].item() <= 0.5):
            pass
        else:
            #Normalization with MinMax Scaler
            minmax = MinMaxScaler()
            minmaxData = pd.DataFrame(minmax.fit_transform(dataframe.values), columns=columns, index=dataframe.index)
            result = [dataframe,minmaxData]
            # Classify data
            self.handleClassifications(result)


    def classifyData(self,data):
        #Classify
        #Note: DATA[0] -> rawData ; DATA[1] -> MINMAX  ;
        data2Use = data[0]
        #Unsupervised Model Predict
        #unsup_pred = self.unsup_model.predict(data2Use)
        #classific_unsup = unsup_pred[0]
        #if(classific_unsup !=0):
        #Supervised Model Predict
        sup_pred = self.sup_model.predict(data2Use)
        classific_sup = sup_pred[0]
        # Handle with Event Classification
        return classific_sup


    def handleClassifications(self,data):
        typ = self.classifyData(data)
        if(typ==0):
            anomalyText = anomalies.get(typ)
            #position 0 for raw data
            self.prepare2Send(anomalyText)

    def ourDict(self,data):
        final = {
        'classification' : str(data.iloc[0]['classification']),
        'date': str(data.iloc[0]['date']),
        'carLocation': str(data.iloc[0]['carLocation']),
        #'longitude' : str(data.iloc[0]['longitude']),
        #'latitude' : str(data.iloc[0]['latitude']),
        
        'pm25' : str(data.iloc[0]['pm25']),
        'pm10': str(data.iloc[0]['pm10']),
        'temperature' : str(data.iloc[0]['temperature']),
        'gas': str(data.iloc[0]['gas']),
        'humidity' : str(data.iloc[0]['humidity']),
        'pressure' : str(data.iloc[0]['pressure']),
        'altitude' : str(data.iloc[0]['altitude'])
        }
        return final

    def prepare2Send(self,classifString):
        #Prepare data before sending to backend
        originalData = self.sensorsData.copy()        
        originalData.pop('carId')
        originalData.pop('timeValue')
        #originalData.pop('carLocation')
        # In PROD environment, next 2 lines deleted
        
        arrangedData = pd.DataFrame([originalData])
        
        #Split carlocation to get latitude & longitude separately
        #location = self.sensorsData.get('carLocation')
        #subStr = location.split(" ")
        #arrangedData['carLocation'] = location
        #arrangedData['latitude'] = subStr[0]
        #arrangedData['longitude'] = subStr[1]
        arrangedData['date'] = self.sensorsData.get('timeValue')
        #Add classification value to array of data
        arrangedData['classification'] = classifString
        #Send data to backend
        diction = self.ourDict(arrangedData)
        self.sendData(diction)



    def sendData(self,json):
        #Save in db cloud
        #Sendo to endpoint POST
        try:
            headers = {"licensePlate" : self.sensorsData.get('carId')}
            workers.queue(jobs.alertCloud, DB_HOST, [json, headers])           
            #res = requests.post("http://34.82.167.68:80/anomaly/create", json=json,headers=headers)
            #print("Sent to backend :D ")
            #print(res.status_code)
        except:
            print("Something went wrong! :( ")
            traceback.print_exc()

#if __name__ == '__main__':
#    main = alertAI()
