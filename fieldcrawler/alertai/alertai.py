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


SUP_MODEL = 'models/svm_gridsearch.sav'
UNSUP_MODEL = 'models/isolationforest.sav'
DB_HOST = 'https://hookb.in/nPyPk9pQ79HaBBGLqdqd'

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
        self.unsup_model = pickle.load(open(UNSUP_MODEL, 'rb'))
        print("Start went good")
        

    def update(self, dataDict):
        self.sensorsData = dataDict
        self.readingsLog.append(dataDict)
        self.readingsLog = self.readingsLog[:10]#limita a 10 últimos logs p.e. para não crescer infinitamente
        self.processData()

    def processData(self):
        # We must handle with unnecessary columns
        raw_data = self.sensorsData.copy()
        raw_data.pop('sensors.id')
        raw_data.pop('sensors.carId')
        raw_data.pop('sensors.carLocation')
        raw_data.pop('sensors.timeValue')
        raw_data.pop('sensors.tags')
        raw_data.pop('sensors.classification')
        #Altitude must be ignred due to misconfiguration
        raw_data.pop('sensors.altitude')
        columns = ['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure']
        dataframe = pd.DataFrame([raw_data])
        if(dataframe['sensors.pm25'].item() <= 0.5):
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
        unsup_pred = self.unsup_model.predict(data2Use)
        classific_unsup = unsup_pred[0]
        if(classific_unsup !=0):
            #Supervised Model Predict
            sup_pred = self.sup_model.predict(data2Use)
            classific_sup = sup_pred[0]
            # Handle with Event Classification
            return classific_sup
        return 0


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
        'longitude' : str(data.iloc[0]['longitude']),
        'latitude' : str(data.iloc[0]['latitude']),
        'pm25' : str(data.iloc[0]['sensors.pm25']),
        'pm10': str(data.iloc[0]['sensors.pm10']),
        'temperature' : str(data.iloc[0]['sensors.temperature']),
        'gas': str(data.iloc[0]['sensors.gas']),
        'humidity' : str(data.iloc[0]['sensors.humidity']),
        'pressure' : str(data.iloc[0]['sensors.pressure'])
        'altitude' : str(data.iloc[0]['sensors.altitude']),
        }
        return final

    def prepare2Send(self,classifString):
        #Prepare data before sending to backend
        originalData = self.sensorsData.copy()        
        originalData.pop('sensors.carId')
        originalData.pop('sensors.timeValue')
        originalData.pop('sensors.carLocation')
        # In PROD environment, next 2 lines deleted
        originalData.pop('sensors.tags')
        originalData.pop('sensors.classification')
        arrangedData = pd.DataFrame(data)
        #Split carlocation to get latitude & longitude separately
        location = self.sensorsData.get('carLocation')
        subStr = location.split(" ")
        arrangedData['latitude'] = subStr[0]
        arrangedData['longitude'] = subStr[1]
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
            headers: {"carID" : self.sensorsData.get('carId')}
            res = requests.post(DB_HOST, json=json,headers=headers)
            print("Sent to backend :D ")
            print(res.text)
        except:
            print("Something went wrong! :( ")
            traceback.print_exc()

#if __name__ == '__main__':
#    main = alertAI()