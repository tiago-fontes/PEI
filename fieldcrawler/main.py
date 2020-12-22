#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Start all the sensors and manage measuarments data flow
"""

import requests
import datetime
import time
import json
from time import gmtime, strftime

#Events Redis system
import jobs
from rqWorker import Workers

from alertai import alertai

from sensors import sensor1
from sensors import sensor2

workers = Workers()

from itsdangerous import (TimedJSONWebSignatureSerializer
                          as Serializer, BadSignature, SignatureExpired)

PORT1 = "/dev/ttyUSB0"
API = "http://0.0.0.0:8085/api"

# EVENTS PY-RQ URL
#DATALAKE_HOST = "http://cehum.ilch.uminho.pt/datalake/api"
DATALAKE_HOST = "http://miradascruzadas.ilch.uminho.pt/api/datalake"
SESNSORS_HOST = DATALAKE_HOST + "/sensors"
BOOT_HOST = DATALAKE_HOST + "/raspberry"

TAGS = "Não existência de fumo, vidros fechados, AC desligado"
CLASSIFICATION = "0"
CARID = "66-ZZ-66"
DEVID = "XX"

class FieldCrawler:

    def __init__(self):

        self.readInterval = 10  # seconds

        self.token2send = ""

        self.sensor1 = None
        self.sensor2 = None

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
                    'timeValue': datetimeNow
                    }
            workers.queue(jobs.bootTime, BOOT_HOST, data)
            #res = requests.post("http://cehum.ilch.uminho.pt/datalake/api/raspberry", json=data, timeout=3)
        except:
            print("Boot time event failed")

    def startListen(self):

        print("Monitoring Sensors...")
        while True:
            dicFinal = {'carId': CARID,
                        'carLocation': '41.5608 -8.3968',
                        'timeValue': strftime("%Y-%m-%d %H:%M:%S", gmtime()),
                        'tags': TAGS,
                        'classification': CLASSIFICATION
                        }
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

    def readSensor1(self):
        measurement = self.sensor1.getMeasurement()
        dataDict = {'pm25': measurement["pm2.5"],
                    'pm10': measurement["pm10"]
                    }
        # print(dataDict)

        return dataDict

    def readSensor2(self):
        measurement = self.sensor2.getMeasurement()
        dataDict = {'temperature': measurement["temperature"],
                    'gas': measurement["gas"],
                    'humidity': measurement["humidity"],
                    'pressure': measurement["pressure"],
                    'altitude': measurement["altitude"]
                    }
        # print(dataDict)

        return dataDict

    def verify_token(self, token):
        s = Serializer('rjvZhLKKCC5crnH6AU9m6Q')
        try:
            data = s.loads(token)
        except SignatureExpired:
            return False  # valid token, but expired
        except BadSignature:
            return False  # invalid token
        return True

    def notifyDataLakeWithAuth(self, dataDict):
        session = requests.Session()
        session.auth = ('VP-35-44', 'VP-35-44')

        verify = self.verify_token(self.token2send)

        # token expired or is invalid
        if verify == False:
            # requests new token with basic auth
            token = session.get(API + "/token")
            json_token = json.loads(token.text)
            self.token2send = json_token["token"]

        # requests post with token auth
        my_headers = {'Authorization': 'Bearer ' + self.token2send}
        res = requests.post(
            API + "/sensors", json=dataDict, headers=my_headers)
        print('response from server:'+res.text)

    def notifyDataLake(self, dataDict):

        # self.notifyDataLakeWithAuth(dataDict)

        #res = requests.post(SESNSORS_HOST, json=dataDict, timeout=1)
        workers.queue(jobs.sensors, SESNSORS_HOST, dataDict)
        print('response from server:'+res.text)

        #print('i would send to the datalake')
        #dictFromResponse = res.json()
        # print(dictFromResponse)

    def notifyAlertAI(self, dataDict):
        
        # CALL MONITOR ALERT (WITH PYTHON-RQ EVENTS MANAGER p.e.)
        self.ml.update(dataDict)
        

    def data2Json(self):
        pass


if __name__ == '__main__':
    main = FieldCrawler()