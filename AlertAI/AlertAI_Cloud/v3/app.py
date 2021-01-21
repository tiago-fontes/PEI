from flask import Flask,jsonify, request
from flask_sqlalchemy import SQLAlchemy
import sqlite3
import os
import pickle
import glob
import pandas as pd
import joblib


#init app
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///alertai.db'
db = SQLAlchemy(app)


#CONSTS
folder = "saves/"
extension = ".sav"


#class
class Capture(db.Model):
	id = db.Column(db.Integer, primary_key=True, autoincrement=True)
	carId = db.Column(db.String(100), nullable=False)
	carLocation = db.Column(db.String(100), nullable=False)
	timeValue = db.Column(db.String(100), nullable=False)
	pm25 = db.Column(db.Float, nullable=False)
	pm10 =  db.Column(db.Float, nullable=False)
	temperature =  db.Column(db.Float, nullable=False)
	gas = db.Column(db.Float, nullable=False)
	humidity =  db.Column(db.Float, nullable=False)
	pressure =  db.Column(db.Float, nullable=False)
	altitude =  db.Column(db.Float, nullable=False)


#class
class Algorithm(db.Model):
	id = db.Column(db.Integer, primary_key=True, autoincrement=True)
	name = db.Column(db.String(100), nullable=False)


#class
class Classification(db.Model):
	id = db.Column(db.Integer, primary_key=True, autoincrement=True)
	capture_id = db.Column(db.Integer, nullable=False)
	algorithm_id = db.Column(db.Integer, nullable=False)
	value = db.Column(db.Integer, nullable=False)



# Delete database file if it exists currently
if os.path.exists("alertai.db"):
	os.remove("alertai.db")

#Create all Models on database
db.create_all()

#Dictionary for Algorithms names and models
AlgoDict = {}

# Data to initialize database with
Algorithms = [
	{"name": "SVM"},
	{"name": "NaiveBayes"},
	{"name": "RandomForest"},
]

def load_models(folder):
	#Get all models to classify
	list_algs = os.listdir(folder)
	#Iterate over Algorithms to classify
	for alg in list_algs:
		alg_file = folder+alg
		#model = pickle.load(open(alg_file, 'rb'))
		model = joblib.load(alg_file)
		name = alg.split(".")[0]
		AlgoDict[name] = model
		a = Algorithm(name=name)
		db.session.add(a)
		db.session.commit()
 
#this function will classify received data
# with all algorithms available
def classify(model,data):
	classification = model.predict(data)
	return classification[0]





#Routing
@app.route('/')
def index():
	return "Welcome to AlertAI Cloud"


#Routing
@app.route('/models',methods = ["GET"])
def models():
	req_data = request.get_json()
	carid=req_data['carId']
	timeV = req_data['timeValue']
	cap = Capture.query.filter_by(carId=carid,timeValue=timeV).first()
	classifs = Classification.query.filter_by(capture_id=cap.id).all()
	models = Algorithm.query.all()
	final_json = {}
	for cla in classifs:
		print("ID ALG NA CLASSIF ", cla.algorithm_id)
		for al in models:
			print("ID ALG NO ALG ", al.id)
			if(cla.algorithm_id==al.id):
				print("SAO MM IGUAIS")
				final_json[al.name] = cla.value
	return jsonify(dict(data=final_json)) # or whatever is required


@app.route('/capture',methods = ["POST"])
def insert_capture():
	req_data = request.get_json()
	capture = Capture(carId=req_data['carId'], carLocation=req_data['carLocation'], timeValue=req_data['timeValue'], pm25=req_data['pm25'],pm10=req_data['pm10'], temperature=req_data['temperature'], gas=req_data['gas'], humidity=req_data['humidity'],pressure=req_data['pressure'], altitude=req_data['altitude'])
	db.session.add(capture)
	db.session.commit()
	id_capture = capture.id
	features = [capture.pm25,capture.pm10,capture.temperature,capture.gas,capture.humidity,capture.pressure]
	data = pd.DataFrame([features])
	for name in AlgoDict:
		classif = int(classify(AlgoDict[name],data))
		id_alg = list(AlgoDict.keys()).index(name) + 1
		c = Classification(capture_id=id_capture,algorithm_id=id_alg,value=classif)
		db.session.add(c)
		db.session.commit()

	return "ok"




if __name__ == "__main__":
	if os.path.exists(".DS_Store"):
		os.remove(".DS_Store")
	load_models(folder)
	app.run(debug=True, host='0.0.0.0',port=5000)