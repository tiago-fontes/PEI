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


SUP_MODEL = 'alertai/svm_test.sav'
UNSUP_MODEL = ''
DB_HOST = 'localhost:7777'

class AlertAI:

    def __init__(self):

        self.sensorsData = None
        self.readingsLog= []
        self.start()

    def start(self):
        #Iniciar modelos e definir configs
        self.sup_model = pickle.load(open(SUP_MODEL, 'rb'))
        #INSERT NEXT LINE TO ADD UNSUPERVISED MODEL 
        #self.unsup_model = pickle.load(open(UNSUP_MODEL 'rb'))
        print("Start went good")
        

    def update(self, dataDict):
        self.sensorsData = dataDict
        self.readingsLog.append(dataDict)
        self.readingsLog = self.readingsLog[:10]#limita a 10 últimos logs p.e. para não crescer infinitamente
        self.processData()

    def processData(self):
        # We must handle with unnecessary columns
        raw_data = self.sensorsData.copy()
        raw_data.pop('carId')
        raw_data.pop('carLocation')
        raw_data.pop('timeValue')
        raw_data.pop('tags')
        raw_data.pop('classification')
        columns = ['pm25','pm10','temperature','gas','humidity','pressure','altitude']
        data = pd.DataFrame([raw_data])
        #Normalization with MinMax Scaler
        minmax = MinMaxScaler()
        minmaxData = pd.DataFrame(minmax.fit_transform(data.values), columns=data.columns, index=data.index)
        #Standarization with StandardScaler
        scaler = StandardScaler()
        standardData = pd.DataFrame(scaler.fit_transform(data.values), columns=data.columns, index=data.index)
        result = [data,minmaxData,standardData]
        # Classify data
        self.classifyData(result)


    def classifyData(self,data):
        #Classify
        #Note: DATA[0] -> rawData ; DATA[1] -> MINMAX  ; DATA[2] -> STANDARDSCALER
        data2Use = data[0]
        classif = self.sup_model.predict(data2Use)
        classification = classif[0]
        # show the input and predicted output
        print("Value = %s \n Classification = %s " % (data2Use, classification))
        print("Classification process done!!")
        if(classification==1):
            self.prepare2Send(classification)
        

    def prepare2Send(self,classification):
        #Prepare data before sending to backend
        beforeSend = self.sensorsData.copy()
        beforeSend.pop('carId')
        beforeSend.pop('timeValue')
        beforeSend.pop('carLocation')
        # In PROD environment, next 2 lines deleted
        beforeSend.pop('tags')
        beforeSend.pop('classification')
        arrangedData = data = pd.DataFrame([beforeSend])
        #Split carlocation to get latitude % longitude separately
        location = self.sensorsData.get('carLocation')
        subStr = location.split(" ")
        arrangedData['longitude'] = subStr[0]
        arrangedData['latitude'] = subStr[1]
        arrangedData['date'] = self.sensorsData.get('timeValue')
        #Add classification value to array of data
        arrangedData['classification'] = classification
        #Send data to backend
        #self.saveData(arrangedData)


    def saveData(self,arrangedData):
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