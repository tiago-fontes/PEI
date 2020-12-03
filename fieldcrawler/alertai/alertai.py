#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import StandardScaler
import pickle


FILE_NAME = 'alertai/svm_test.sav'
DB_HOST = ''

class AlertAI:

    def __init__(self):

        self.sensorsData = None
        self.readingsLog= []
        self.start()

    def start(self):
        #Iniciar modelos e definir configs
        self.model = pickle.load(open(FILE_NAME, 'rb'))
        print("Start went good")
        #pass

    def update(self, dataDict):
        self.sensorsData = dataDict
        self.readingsLog.append(dataDict)
        self.readingsLog = self.readingsLog[:10]#limita a 10 últimos logs p.e. para não crescer infinitamente
        self.processData()

    def processData(self):
        #Process / Call and check models
        #Chamar outras funções
        #LEMBRAR DE TIRAR A PROXIMA LINHA DE COMENTARIO PARA PROD ENV -------------------------------------------------------------
        raw_data = self.sensorsData
        raw_data.pop('carId')
        raw_data.pop('carLocation')
        raw_data.pop('timeValue')
        raw_data.pop('tags')
        raw_data.pop('classification')
        columns = ['id','carId','carLocation','timeValue','pm25','pm10','temperature','gas','humidity','pressure','altitude']
        data = pd.DataFrame([raw_data])
        
        #data = raw_data[['sensors.pm25','sensors.pm10','sensors.temperature','sensors.gas','sensors.humidity','sensors.pressure','sensors.altitude']]
        #Normalization with MinMax
        minmax = MinMaxScaler()
        minmaxData = pd.DataFrame(minmax.fit_transform(data.values), columns=data.columns, index=data.index)
        #Standarization with StandardScaler
        scaler = StandardScaler()
        standardData = pd.DataFrame(scaler.fit_transform(data.values), columns=data.columns, index=data.index)
        result = [data,minmaxData,standardData]
        self.classifyData(result)
        #return result
        #etc


    def classifyData(self,data):
        #Classify
        #Note: DATA[0] -> rawData ; DATA[1] -> MINMAX  ; DATA[2] -> STANDARDSCALER
        data2Use = data[0]
        classif = self.model.predict(data2Use)
        # show the inputs and predicted outputs
        #for i in range(len(data2Use)):
            #print("Value = %s, Classification = %s" % (data2Use[i], classif[i]))
        print("Value = %s \n Classification = %s " % (data2Use, classif[0]))
        print("Classification process done!!")
        #self.prepare2Send(self.sensorsData, classif)
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
        #return arrangedData
        self.saveData(arrangedData)


    def saveData(self,arrangedData):
        #Save in db cloud
        #Sendo to endpoint POST
        try:
            #res = requests.post(DB_HOST, json=arrangedData)
            print("Sent to backend :D ")
        except:
            print("Something went wrong! :( ")

#if __name__ == '__main__':
#    main = alertAI()