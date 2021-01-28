import pandas as pd
import numpy as np


file = r'C:\Users\JCost\OneDrive\Ambiente de Trabalho\Code\PEI\AlertAI\data\clean_data_with_lixo.csv'
name_new_file = 'clean_data_with_lixo_13_01.csv'

print('Job Started...')

rawData = pd.read_csv(file)
normal_temperature_values = rawData[rawData['sensors.classification'] < 3]
errored_raw = rawData[rawData['sensors.classification'] == 3]
num_error = errored_raw.shape[0]


print('Datasets Loaded....')

# def get_temperature(x):
#     m,std = 20, 4
#     return np.random.normal(m,std,1)[0]

# new_df = errored_raw.apply(lambda x: get_temperature(x) if x.name == 'sensors.temperature' else x)
# errored_raw['sensors.temperature'] = errored_raw['sensors.temperature'].apply(get_temperature)

m,std = 20, 4
new_temps = np.random.normal(m,std,num_error)
idx = errored_raw.index

for row in range(num_error):
    errored_raw.loc[idx[row],'sensors.temperature'] = new_temps[row]





new_df = normal_temperature_values.append(errored_raw)
print('Dataset edited...')

new_df.to_csv(name_new_file, index=False)

print('Saved....\nJob Done')