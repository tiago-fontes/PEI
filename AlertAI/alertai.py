#!/usr/bin/env python3
# -*- coding: utf-8 -*-

#This file was created in order to provide the AlertAI module
#which is responsible for classify data and report anomalies

import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import StandardScaler
import pickle


class AlertAI:

    def __init__(self):
        
        self.sensorsData = None
        self.readingsLog= []
        self.start()
        self.model = None

    def start(self):
        #Iniciar modelos e definir configs
        self.model = pickle.load(open('filename', 'rb'))
        pass
        
    def update(self, dataDict):
        self.sensorsData = dataDict
        self.readingsLog.append(dataDict)
        self.readingsLog = self.readingsLog[:10]#limita a 10 últimos logs p.e. para não crescer infinitamente
        self.processData()
        
    def processData(self):
        #Process / Call and check models
        #Chamar outras funções
        raw_data = self.sensorsData
        data = raw_data[['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.himidity','sensors.pressure','sensors.altitude']]
        #Normalization with MinMax
        minmax = MinMaxScaler()
        minmaxData = minmax.fit_transform(data)
        scaler = StandardScaler()
        standardData = scaler.fit_transform(data)
        resul = [data,minmaxData,standardData]
        return result
        #etc


    def classifyData(self,data):
        #Classify
        classif = self.model.predict(data)
        # show the inputs and predicted outputs
        for i in range(len(data)):
            print("Value = %s, Classification = %s" % (data[i], classif[i]))
        print("Classification process done!!")
        #etc

    def prepare2Send(self, data, classification):
        #Prepare data before sending to backend
        #Arrange in structure defined, according to all variables
        #Note: argument data represents all the variables collect, i.e., includes id, localization, etc
        classif = classification[0]
        arrangedData = data[['sensors.timevalue','sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.himidity','sensors.pressure','sensors.altitude']]
        location = data[['sensors.carlocation']]
        #Split carlocation to get latitude % longitude separately
        subStr = location.split(" ")
        arrangedData['latitude'] = subStr[0]
        arrangedData['longitude'] = subStr[1]
        #Add classification value to array of data
        arrangedData['classification'] = classif
        return arrangedData 

      
    def saveData(self,arrangedData):
        #Save in db cloud
        #Sendo to endpoint POST
        try:
            res = requests.post(DB_HOST, json=arrangedData)
            print("Sent to backend :D ")
        except:
            print("Something went wrong! :( ")

#if __name__ == '__main__':
#    main = alertAI()
