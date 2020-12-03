#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Abstract Class
"""

from abc import ABC, abstractmethod

class Sensor(ABC):

    def __init__(self, port="/dev/ttyUSB0"):
        self.port = port
        self.sensor = None
        self.measurement = None
        
    @abstractmethod
    def start(self):
        pass
    
    @abstractmethod
    def getMeasurement(self):
        pass

