# Test File for AlertAI module


import pandas as pd
import numpy as np
import alertai as AI
import getLast


if __name__ == '__main__':
    variable = getLast.getLast()
    diction = {
    'sensors.id': 3237,
     'sensors.carId': 'VP-35-44',
     'sensors.carLocation': '41.5608 -8.3968',
      'sensors.timeValue': '2020-12-11 18:16:24',
      'sensors.pm25': 1.8, 
      'sensors.pm10': 3.6, 
      'sensors.temperature': 21.5753125,
       'sensors.gas': 10300, 
       'sensors.humidity': 58.49512197025919, 
       'sensors.pressure': 992.4350184780056, 
       'sensors.altitude': 174.75835831424578, 
       'sensors.tags': 'Não existência de fumo, janelas fechadas, AC desligado', 
       'sensors.classification': 0}
    #print(diction)
    #print(variable)
    ml = AI.AlertAI()
    ml.start()
    pre_process = ml.processData(diction)
    print("JA ACABEIII")



