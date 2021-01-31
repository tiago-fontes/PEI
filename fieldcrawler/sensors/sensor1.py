#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
SDS001 Sensor
Note: this method runs countinously = low life time for sensor
Sensor has 2 modes of reading. This is ok to test, in production use a start stop method
"""

import time

#This way it can be run as main or as a package
if __name__ != '__main__':
    from . import sensor
    #from libraries.sds011 import sds011
    #import libraries.sds011.sds011 as sds011
    
    from libraries import py_sds011
    #import sds011
else:
    import sensor
    #import sds011#pip3 install sds011
    from libraries import py_sds011

class Sensor1(sensor.Sensor):

    def __init__(self, port="/dev/ttyUSB0", time=3):
        super().__init__()
        self.port = port
        self.readInterval = time#seconds to wait between fan up and off - the longer the preciser the read
        
        self.start()
        
    def start(self):
        self.sensor = py_sds011.SDS011("/dev/ttyUSB0", use_query_mode=True)
        print("Sensor1: Running on port " + self.port)
        self.sensor.sleep(sleep=False)
        
    #Nota: this method is not ideal: sensor will be dead before one year with it always running non stop
    #Ideally start and stop it each read interval, and wait 1 minute + bettween each read
    def sensorOff(self):
        self.sensor.sleep(sleep=True)
        
    def sensorOn(self):
        self.sensor.sleep(sleep=False)
        
    def getMeasurement(self):
        #Liga a ventoinha e laser
        #self.sensorOn()
        #time.sleep(3)
        self.measurement = self.sensor.query()
        data = {"pm2.5":self.measurement[0],
                "pm10":self.measurement[1],
                }
        #self.sensorOff()
        return(data)


# Get das portas USB automaticamente. Not used, lways connected to the same 1st one
def getUsbPortsUsed():
    import subprocess
    try:
        with subprocess.Popen(['ls /dev/ttyUSB*'], shell=True, stdout=subprocess.PIPE) as f:
            usbs = f.stdout.read().decode('utf-8')
        usbs = usbs.split('\n')
        usbs = [usb for usb in usbs if len(usb) > 3]
    except Exception as e:
        print('No USB available')
    return usbs

if __name__ == '__main__':
    sensor = Sensor1()
    sensor.getMeasurement()
    
