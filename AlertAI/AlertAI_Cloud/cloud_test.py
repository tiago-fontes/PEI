import requests

#BASE = "http://localhost:5000/"
BASE = "http://35.187.57.30:5000/"

json = {
	   'carId': 'VP-35-44',
     'carLocation': '41.5608 -8.3968',
      'timeValue': '2020-12-11 18:16:24',
      'pm25': 1.8, 
      'pm10': 3.6, 
      'temperature': 21.5753125,
      'gas': 10300, 
      'humidity': 58.49512197025919, 
      'pressure': 992.4350184780056, 
      'altitude': 174.75835831424578, 
}



response = requests.post(BASE + "capture", json=json)
print(response.text)