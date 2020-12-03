#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
BME680 Sensor
"""

from busio import I2C
import adafruit_bme680#pip3 install adafruit-circuitpython-bme680
import time
import board

if __name__ != '__main__':
    from . import sensor
    #from libraries.adafruit_bme680 import adafruit_bme680
else:
    import sensor

class Sensor2(sensor.Sensor):

    def __init__(self, port=I2C(board.SCL, board.SDA), interval=3):
        super().__init__()
        self.port = port
        self.readInterval = interval#seconds
        
        self.start()
        
    def start(self):
        # Create library object using Bus I2C port
        self.sensor = adafruit_bme680.Adafruit_BME680_I2C(self.port)
        # change this to match the location's pressure (hPa) at sea level
        self.sensor.sea_level_pressure = 1013.25
        print("Sensor2: Running on Bus I2C port / Interval")
        
        
    def getMeasurement(self):
        data = {"temperature": self.sensor.temperature,
                "gas": self.sensor.gas,
                "humidity": self.sensor.humidity,
                "pressure": self.sensor.pressure,
                "altitude": self.sensor.altitude,
                }
        return(data)

if __name__ == '__main__':
    bme680 = Sensor2()
    while True:
        data = bme680.getMeasurement()
        print(data)
        """
        print("\nTemperature: %0.1f C" % bme680.temperature)
        print("Gas: %d ohm" % bme680.gas)
        print("Humidity: %0.1f %%" % bme680.humidity)
        print("Pressure: %0.3f hPa" % bme680.pressure)
        print("Altitude = %0.2f meters" % bme680.altitude)
        """

        time.sleep(2)
