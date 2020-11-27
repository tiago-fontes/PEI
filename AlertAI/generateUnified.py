# This file was created to merge 2 diffenret datasets into one:
# we are going to join the Sensor 1 set and Sensor 2 set.

# Note that this is going to generate to this current directory
# the .csv file with combined data.


import os
import requests
import shutil
import pandas as pd

###### COMMENT IF NOT NEEDED #######
cur_dir = r"C:\Users\JCost\OneDrive\Ambiente de Trabalho\Code\PEI\AlertAI" 
###################################


def joinData(file1,file2):
    try:
        sensor1Data = pd.read_csv(r"data\sensor1_raw_all.csv")
        sensor2Data = pd.read_csv(r"data\sensor2_raw_all.csv")
    except:
        print("Failure!! Files not found")
        exit()
    
    part2 = sensor2Data[['sensor2.temperature', 'sensor2.gas', 'sensor2.humidity', 'sensor2.pressure', 'sensor2.altitude']]
    result = sensor1Data.join(part2)
    return result

if __name__ == '__main__':

    # Variables indication
    f1 = r"data\sensor1_raw_all.csv"
    f2 = r"data\sensor2_raw_all.csv"
    final = r"data\unified_all.csv"

    ###### COMMENT IF NOT NEEDED #######
    os.chdir(cur_dir)
    ###################################
    
    # Join the Data
    unified_Data = joinData(f1,f2)

    # Organize columns position
    new_cols = ['sensor1.id', 'sensor1.carId', 'sensor1.carLocation',
       'sensor1.timeValue', 'sensor1.pm25', 'sensor1.pm10', 'sensor2.temperature', 'sensor2.gas',
       'sensor2.humidity', 'sensor2.pressure', 'sensor2.altitude', 'sensor1.tags','sensor1.classification']
    unified_Data=unified_Data.reindex(columns=new_cols)
    
    # Change columns name
    new_names= ['id', 'carId', 'carLocation','timeValue', 'pm25',
                'pm10', 'temperature', 'gas','humidity', 'pressure', 
                'altitude', 'tags','classification']
    unified_Data.columns = new_names

    # Save the new unified Data
    unified_Data.to_csv(final, index=False)
    
    # Check new dataset
    print(unified_Data.head())
