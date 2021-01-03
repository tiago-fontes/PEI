from flask import Flask,jsonify, request
from flask_sqlalchemy import SQLAlchemy
import sqlite3
import os
import pickle

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
	value = db.Column(db.Integer, nullable=False)
	capture_id = db.Column(db.Integer, nullable=False)
	algorithm_id = db.Column(db.Integer, nullable=False)



# Delete database file if it exists currently
if os.path.exists("alertai.db"):
    os.remove("alertai.db")

#Create all Models on database
db.create_all()


# Data to initialize database with
Algorithms = [
    {"name": "SVM"},
    {"name": "NaiveBayes"},
    {"name": "RandomForest"},
]

# iterate over the Algorithms structure and populate the database
for alg in Algorithms:
    a = Algorithm(name=alg.get("name"))
    db.session.add(a)

db.session.commit()



#this function will classify received data
# with all algorithms available
def classify(model,data):
	classification = model.predict(data)
	return classification



#Routing
@app.route('/')
def index():
	return "Simple API with Flask"


@app.route('/capture',methods = ["GET","POST"])
def insert_capture():
    req_data = request.get_json()
    capture = Capture(carId=req_data['carId'], carLocation=req_data['carLocation'], timeValue=req_data['timeValue'], pm25=req_data['pm25'],pm10=req_data['pm10'], temperature=req_data['temperature'], gas=req_data['gas'], humidity=req_data['humidity'],pressure=req_data['pressure'], altitude=req_data['altitude'])
    db.session.add(capture)
    db.session.commit()
    """
    data = ....
    #Iterate over Algorithms to classify
    for alg in Algorithms:
    	a = Algorithm(name=alg.get("name"))
    	model = pickle.load(open(folder+a+extension, 'rb'))
    	class = classify(model,data)
    	c = Classification(.... REVER POR CAUSA DOS IDS)
    	db.session.add(c)
    	db.session.commit()

 	"""

    return "ok"




if __name__ == "__main__":
	app.run(debug=True, host='0.0.0.0',port=5000)