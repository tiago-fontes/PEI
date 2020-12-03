import requests
import pandas as pd
import io

url = "http://cehum.ilch.uminho.pt/datalake/sensors/last/csv"

def getLast():
    try:
    	urlData = requests.get(url).content
    except:
    	print("Failure!! Please check url from DataLake")
    	exit()
    rawData = pd.read_csv(io.StringIO(urlData.decode('utf-8')))
    #print(rawData)
    return rawData


if __name__ == '__main__':
    r = getLast()
    #print(type(r))