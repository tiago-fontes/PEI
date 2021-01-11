# This file was created to get the latest dump of collected data
# from RideCare Datalake

# Note that this is going to donwload to this current directory
# the .csv file with RAW DATA


import os
import requests
import shutil
import pandas as pd


url = "http://cehum.ilch.uminho.pt/datalake/sensors/all/csv"


def getRawData():
    try:
        r= requests.get(url, verify=False,stream=True)
    except:
        print("Failure!! Please check url from DataLake")
        exit()
        
    r.raw.decode_content = True
    with open("data/raw_data_com_lixo.csv", 'wb') as f1:
        shutil.copyfileobj(r.raw, f1)
    print("Success. Data from both sensors collected")

if __name__ == '__main__':
    getRawData()
    #df = pd.read_csv("data/raw_data.csv")
    #print(df.head())
