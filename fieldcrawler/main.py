#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Start all the sensors and manage measuarments data flow
"""

import requests, datetime, time, json
from time import gmtime, strftime

#Events Redis system
import jobs
from rqWorker import Workers

from alertai import alertai

from sensors import sensor1
from sensors import sensor2
from gpsReceiver import G_MOUSE

workers = Workers()


PORT1 = "/dev/ttyUSB0"
#API = "http://0.0.0.0:8085/api"
API = "http://35.189.102.211/api"

# EVENTS PY-RQ URL
#DATALAKE_HOST = "http://cehum.ilch.uminho.pt/datalake/api"
#DATALAKE_HOST = "http://miradascruzadas.ilch.uminho.pt/api/datalake"
API_HOST = "http://35.189.102.211/api"
SESNSORS_HOST = API_HOST + "/datalake/sensors"
BOOT_HOST = API_HOST + "/datalake/raspberry"

#TAGS = "Não existência de fumo, vidros fechados, AC desligado"
#CLASSIFICATION = "0"
CARID = "AA-11-AA"
#DEVID = "2"

class FieldCrawler:

    def __init__(self):

        self.readInterval = 10  # seconds

        self.token2send = ""

        self.sensor1 = None
        self.sensor2 = None
        self.g_mouse = G_MOUSE()

        #Save raspberry boot datatime
        self.bootTime()

        self.ml = alertai.AlertAI()

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

    def bootTime(self):
        try:
            datetimeNow = strftime("%Y-%m-%d %H:%M:%S", gmtime())
            data = {'carId': CARID,
                    'deviceId': '',
                    'timeValue': datetimeNow
                    }
            workers.queue(jobs.bootTime, BOOT_HOST, data)
            #res = requests.post("http://34.105.216.153/datalake/default/api/raspberry", json=data, timeout=3)
            #print(res.text)
        except:
            print("Boot time event failed")

    def startListen(self):

        print("Monitoring Sensors...")
        while True:
            dicFinal = {'carId': CARID, }

            try:
                dicFinal.update(self.readG_MOUSE())
            except:
                print("G_MOUSE read failed")


            moreInfo =  {
                        'timeValue': strftime("%Y-%m-%d %H:%M:%S", gmtime())
                        }

            dicFinal.update(moreInfo)

            try:
                dicFinal.update(self.readSensor1())
            except:
                print("Sensor1 read failed")
            try:
                dicFinal.update(self.readSensor2())
            except:
                print("Sensor2 read failed")
            print(dicFinal)
            try:
                self.notifyDataLake(dicFinal)
            except:
                #print("Notify DataLake failed")
                pass
            self.notifyAlertAI(dicFinal)
            time.sleep(self.readInterval)


    def readG_MOUSE(self):
        try:
            measurement = self.g_mouse.getMeasurement()
            mesValue = measurement["carLocation"]
        except:
            mesValue = "41.5608 -8.3968"
        dataDict = {
                    'carLocation': mesValue
                   }

        return dataDict

    def readSensor1(self):
        measurement = self.sensor1.getMeasurement()
        dataDict = {'pm25': measurement["pm2.5"],
                    'pm10': measurement["pm10"]
                    }

        return dataDict

    def readSensor2(self):
        measurement = self.sensor2.getMeasurement()
        dataDict = {'temperature': measurement["temperature"],
                    'gas': measurement["gas"],
                    'humidity': measurement["humidity"],
                    'pressure': measurement["pressure"],
                    'altitude': measurement["altitude"]
                    }

        return dataDict


    def notifyDataLakeWithAuth(self, dataDict):
        session = requests.Session()
        session.auth = ('VP-35-44', 'VP-35-44')
        user = "VP-35-44"
        id ="VP-35-44"
        
        # requests post with token auth
        my_headers = {'Authorization': 'Bearer ' + self.token2send}
        res = requests.post(API + "/datalake/sensors", json=dataDict, headers=my_headers)
        print(res.text)
        # token expired or is invalid
        if (res.text == "Unauthorized Access"):
            try:
               token = session.get(API + "/token")
               json_token = json.loads(token.text)
               self.token2send = json_token["token"]

               my_headers = {'Authorization': 'Bearer ' + self.token2send}
               res = requests.post(API + "/sensors", json=dataDict, headers=my_headers)
               print('response from server:'+token.text)

            except:
               print("Error")
            print('response from server:'+res.text)

    def notifyDataLake(self, dataDict):
        #self.notifyDataLakeWithAuth(dataDict)
        #res = requests.post(SESNSORS_HOST, json=dataDict, timeout=1)
        workers.queue(jobs.sensors, SESNSORS_HOST, dataDict)


    def notifyAlertAI(self, dataDict):
        
        # CALL MONITOR ALERT (WITH PYTHON-RQ EVENTS MANAGER p.e.)
        self.ml.update(dataDict)
        #pass
        

    def data2Json(self):
        pass


if __name__ == '__main__':
    main = FieldCrawler()
