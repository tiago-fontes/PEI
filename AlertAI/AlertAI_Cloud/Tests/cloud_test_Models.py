import requests

BASE = "http://34.76.239.59:5000/"


params = {'licensePlate': "VP-35-44", 'timeValue': "2020-12-11 18:16:24" }

response = requests.get(BASE + "models",params=params)
print(response.text)