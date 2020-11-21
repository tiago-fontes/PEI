# This file was created to get the latest dump of collected data
# from RideCare Datalake

# Note that this is going to donwload to this current directory
# the .csv file with RAW DATA


import os
import requests
import shutil
import pandas as pd


url_sensor1 = "http://cehum.ilch.uminho.pt/datalake/csv.load"
url_sensor2 = "http://cehum.ilch.uminho.pt/datalake/csv.load/bme680"


def getRawData():
    try:
        r_1 = requests.get(url_sensor1, verify=False,stream=True)
        r_2 = requests.get(url_sensor2, verify=False,stream=True)
    except:
        print("Failure!! Please check url from DataLake")
        exit()
        
    r_1.raw.decode_content = True
    with open("sensor1_raw.csv", 'wb') as f1:
        shutil.copyfileobj(r_1.raw, f1)
    r_2.raw.decode_content = True    
    with open("sensor2_raw.csv", 'wb') as f2:
        shutil.copyfileobj(r_2.raw, f2)
    print("Success. Data from both sensors collected")

if __name__ == '__main__':
    getRawData()
    df1 = pd.read_csv("sensor1_raw.csv")
    df2 = pd.read_csv("sensor2_raw.csv")
    #print(df2.head())
