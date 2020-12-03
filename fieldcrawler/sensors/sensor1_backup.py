#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
SDS001 Sensor
"""

#This way it can be run as main or as a package
if __name__ != '__main__':
    from . import sensor
    #from libraries.sds011 import sds011
    #import libraries.sds011.sds011 as sds011
    
    from libraries.sds011 import sds011
    #import sds011
else:
    import sensor
    import sds011#pip3 install sds011

class Sensor1(sensor.Sensor):

    def __init__(self, port="/dev/ttyUSB0"):
        super().__init__()
        self.port = port
        self.readInterval = 1#minuts from 0 to 30 - floats not permited
        
        self.start()
        
    def start(self):
        self.sensor = sds011.SDS011(port=self.port)
        print("Sensor1: Running on port " + self.port + " / Interval: " + str(self.readInterval) + " minuts")
        self.sensor.set_working_period(rate=self.readInterval)
        #self.getMeasurement()
        
    def getMeasurement(self):
        self.measurement = self.sensor.read_measurement()
        return(self.measurement)

if __name__ == '__main__':
    main = Sensor1("teste")
