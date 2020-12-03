#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Start all the sensors and manage measuarments data flow
"""

import requests, datetime, time
from time import gmtime, strftime

from sensors import sensor1
from sensors import sensor2

PORT1 = "/dev/ttyUSB0"
DATALAKE_HOST = "http://cehum.ilch.uminho.pt/ridecare/api/"

class FieldCrawler:

    def __init__(self):
        
        self.readInterval = 10#seconds
        
        self.sensor1 = None
        self.sensor2 = None
        
        self.sensorsStart()
        self.startListen()
        
    def sensorsStart(self):
        #self.sensor1 = SDS011(port=self.port1,use_database=False)
        try:
            self.sensor1 = sensor1.Sensor1(PORT1)
            self.sensor2 = sensor2.Sensor2()
        except:
            print("Sensors start failed, check ports or if active")
            print("Trying to start again")
            self.sensorsStart()
        
    def startListen(self):
        print("Monitoring Sensors...")
        while True:
            #NOTA: Sensor 1 reading is a blocking function: everything stops waiting for it's response,
            #so we will need to run it as a thread for not blocking sensor 2
            #Note2: sensor 1 also automatically manages the time, but sensor 2 could be manually set with a timer,
            #so this is provisory depending on the sensor 2 logic
            try:
                self.readSensor1()
            except:
                print("Sensor1 read failed")
            try:
                self.readSensor2()
            except:
                print("Sensor2 read failed")
            time.sleep(self.readInterval)

    def readSensor1(self):
        measurement = self.sensor1.getMeasurement()
        print(measurement)
        dataDict = {'sensor':'sensor1',
                    'carId': 'VP-35-44',
                    'carLocation': '41.5608 -8.3968',
                    'timeValue': datetime.datetime.strftime(measurement["timestamp"], '%Y-%m-%d %H:%M:%S'),
                    'pm25': str(measurement["pm2.5"]),
                    'pm10': str(measurement["pm10"]),
                    'devid': str(measurement["devid"])
                    }
        try:
            self.notifyDataLake("sensor1", dataDict)
        except:
            print("Notify DataLake failed")
        self.notifyMonitorAlert("sensor1", dataDict)
        
    def readSensor2(self):
        measurement = self.sensor2.getMeasurement()
        dataDict = {'sensor':'sensor2',
                    'carId': 'VP-35-44',
                    'carLocation': '41.5608 -8.3968',
                    'timeValue': strftime("%Y-%m-%d %H:%M:%S", gmtime()),
                    'temperature': measurement["temperature"],
                    'gas': measurement["gas"],
                    'humidity': measurement["humidity"],
                    'pressure': measurement["pressure"],
                    'altitude': measurement["altitude"],
                    }
        try:
            self.notifyDataLake("sensor2", dataDict)
        except:
            print("Notify DataLake failed")
        self.notifyMonitorAlert("sensor2", dataDict)
        
    def notifyDataLake(self, sensor, dataDict):
        res = requests.post(DATALAKE_HOST + sensor, json=dataDict)
        print('response from server:'+res.text)
        #dictFromResponse = res.json()
        #print(dictFromResponse)

    def notifyMonitorAlert(self, sensor, dataDict):
        ### CALL MONITOR ALERT (WITH PYTHON-RQ EVENTS MANAGER p.e.)
        pass
    
    def data2Json(self):
        pass

if __name__ == '__main__':
    main = FieldCrawler()
