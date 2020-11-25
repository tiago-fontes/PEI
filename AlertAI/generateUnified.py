# This file was created to merge 2 diffenret datasets into one:
# we are going to join the Sensor 1 set and Sensor 2 set.

# Note that this is going to generate to this current directory
# the .csv file with combined data.


import os
import requests
import shutil
import pandas as pd



def joinData():
    try:
        sensor1Data = pd.read_csv("sensor1_raw.csv")
        sensor2Data = pd.read_csv("sensor2_raw.csv")
    except:
        print("Failure!! Files not found")
        exit()
    
    part2 = sensor2Data[['sensor2.temperature', 'sensor2.gas', 'sensor2.humidity', 'sensor2.pressure', 'sensor2.altitude']]
    result = sensor1Data.join(part2)
    return result

if __name__ == '__main__':
    unified_Data = joinData()
    unified_Data.to_csv("unified.csv")
    print(unified_Data.head())
    #print(df2.head())
