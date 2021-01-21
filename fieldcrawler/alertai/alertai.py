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
DB_HOST = 'http://requestbin.net/r/1b0mi5m1'


#Here we have declared all the anomalies that RideCare system is able to identify
# 0 means a normal scenario, so there is nothing anormal in this situatios
# 1 means smoke detection inside the vehicle, produced for exemple with cigarette
# 2 means stink detection inside the vehicle, produced for exemple with leftover food, bad smells, etc
anomalies = {
        0: "normal",
        1: "smoke",
        2: "stink",
    }


# This class represents all the logic for Classification of raw data and anomaly detection
# Here we have spcecified the process of handling the received information, classification and report to Backend System
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


    # This function is responsible for drop the columns we dont need, in order to get data ready for classification    
    def processData(self):
        # We must handle with unnecessary columns
        raw_data = self.sensorsData.copy()
        raw_data.pop('carId')
        raw_data.pop('carLocation')
        raw_data.pop('timeValue')
        raw_data.pop('tags')
        raw_data.pop('classification')
        #Altitude must be ignred due to misconfiguration
        raw_data.pop('altitude')
        columns = ['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure']
        dataframe = pd.DataFrame([raw_data])
        if(dataframe['pm25'].item() <= 0.5):
            pass
        else:
            # Classify data
            self.handleClassifications(dataframe)


    # This function is going to callout the Algorithm in order to classify received data
    def classifyData(self,data):
        #Classify
        sup_pred = self.sup_model.predict(data)
        classific_sup = sup_pred[0]
        # Handle with Event Classification
        return classific_sup


    # This function is going to translate the classification given by the algorithm into the anomalies registered in the above dictionay    
    def handleClassifications(self,data):
        typ = self.classifyData(data)
        if(typ==0):
            anomalyText = anomalies.get(typ)
            #position 0 for raw data
            self.prepare2Send(anomalyText)


    # Simple function to build up the response to backend system
    def ourDict(self,data):
        final = {
        'classification' : str(data.iloc[0]['classification']),
        'date': str(data.iloc[0]['date']),
        'longitude' : str(data.iloc[0]['longitude']),
        'latitude' : str(data.iloc[0]['latitude']),
        'pm25' : str(data.iloc[0]['pm25']),
        'pm10': str(data.iloc[0]['pm10']),
        'temperature' : str(data.iloc[0]['temperature']),
        'gas': str(data.iloc[0]['gas']),
        'humidity' : str(data.iloc[0]['humidity']),
        'pressure' : str(data.iloc[0]['pressure']),
        'altitude' : str(data.iloc[0]['altitude'])
        }
        return final


    #Prepares all the information 
    def prepare2Send(self,classifString):
        #Prepare data before sending to backend
        originalData = self.sensorsData.copy()        
        originalData.pop('carId')
        originalData.pop('timeValue')
        originalData.pop('carLocation')
        # In PROD environment, next 2 lines deleted
        originalData.pop('tags')
        originalData.pop('classification')
        
        
        arrangedData = pd.DataFrame([originalData])
        
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



        # Function that sends the HTTP request
    def sendData(self,json):
        #Save in db cloud
        #Sendo to endpoint POST
        try:
            headers = {"carID" : self.sensorsData.get('carId')}
            workers.queue(jobs.alertCloud, DB_HOST, [json, headers])
            #res = requests.post(DB_HOST, json=json,headers=headers)
            #print("Sent to backend :D ")
            #print(res.text)
        except:
            print("Something went wrong! :( ")
            traceback.print_exc()

#if __name__ == '__main__':
#    main = alertAI()