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


SUP_MODEL = 'models/svm_gridsearch.sav'
UNSUP_MODEL = 'models/isolationforest.sav'
DB_HOST = '/t/zj03z-1607811948/post'

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

    def processData(self,data):
        # We must handle with unnecessary columns
        #raw_data = self.sensorsData.copy()
        raw_data = data
        #raw_data.pop('carId')
        #raw_data.pop('carLocation')
        #raw_data.pop('timeValue')
        #raw_data.pop('tags')
        #raw_data.pop('classification')
        #Altitude must be ignred due to misconfiguration
        #raw_data.pop('altitude')
        columns = ['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure']
        #data = pd.DataFrame([raw_data])
        data = raw_data[['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure']]
        if(data['sensors.pm25'].item() <= 0.5):
            pass
        else:
            #Normalization with MinMax Scaler
            minmax = MinMaxScaler()
            minmaxData = pd.DataFrame(minmax.fit_transform(data.values), columns=columns, index=data.index)
            result = [data,minmaxData]
            # Classify data
            self.handleClassifications(result)


    def classifyData(self,data):
        #Classify
        #Note: DATA[0] -> rawData ; DATA[1] -> MINMAX  ; DATA[2] -> STANDARDSCALER
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
        if(typ!=0):
            anomalyText = anomalies.get(typ)
            self.prepare2Send(anomalyText)
        

    def prepare2Send(self,classifString):
        #Prepare data before sending to backend
        originalData = self.sensorsData.copy()
        originalData.pop('carId')
        originalData.pop('timeValue')
        originalData.pop('carLocation')
        # In PROD environment, next 2 lines deleted
        originalData.pop('tags')
        originalData.pop('classification')
        arrangedData = data = pd.DataFrame([originalData])
        #Split carlocation to get latitude & longitude separately
        location = self.sensorsData.get('carLocation')
        subStr = location.split(" ")
        arrangedData['latitude'] = subStr[0]
        arrangedData['longitude'] = subStr[1]
        arrangedData['date'] = self.sensorsData.get('timeValue')
        #Add classification value to array of data
        arrangedData['classification'] = classificString
        #Send data to backend
        self.sendData(arrangedData)


    def sendData(self,arrangedData):
        #Save in db cloud
        #Sendo to endpoint POST
        try:
            headers: {"carID" : self.sensorsData.get('carId')}
            res = requests.post(DB_HOST, json=arrangedData,headers=headers)
            print("Sent to backend :D ")
            print(res.text)
        except:
            print("Something went wrong! :( ")

#if __name__ == '__main__':
#    main = alertAI()