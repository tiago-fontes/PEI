#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
G-MOUSE (Receptor GPS)
"""

from pyembedded.gps_module.gps import GPS

class G_MOUSE():

    def __init__(self, port= "/dev/ttyACM0"):
        super().__init__()
        self.port = port
        self.gps = None
        self.start()
        
    def start(self):
        gps = GPS(port = self.port, baud_rate=9600)
        self.gps = gps
        print("G-MOUSE (Receptor GPS): Running on port " + self.port)
        
        
    def getMeasurement(self):
        coordinates = self.gps.get_lat_long()

        if (coordinates != None):
            coordinates = str(coordinates[0]) + ' ' +  str(coordinates[1])
        
        data = {"carLocation": coordinates,
               }
        return(data)

if __name__ == '__main__':
    g_mouse = G_MOUSE()
    data = g_mouse.getMeasurement()
    print(data)