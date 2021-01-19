#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
API router
"""

import requests, base64
from flask import Blueprint, request, Response, jsonify
from flask_httpauth import HTTPTokenAuth, HTTPBasicAuth

from encryptor import Encryptor

#RQ
from rq import Queue
from rqWorker import Workers
import jobs

from app import app

from itsdangerous import (TimedJSONWebSignatureSerializer
                          as Serializer, BadSignature, SignatureExpired)

DATALAKE_HOST = "http://34.105.216.153/datalake/default/api"
SENSORS_HOST = DATALAKE_HOST + "/sensors"
BOOT_HOST = DATALAKE_HOST + "/raspberry"
ALERTCLOUD = "http://35.205.66.70:5000/capture "
BACKEND = "http://34.82.167.68:80/anomaly/create"

apiRoute = Blueprint('api', __name__,  template_folder='views')

workers = Workers()

basicAuth = HTTPBasicAuth()
tokenAuth = HTTPTokenAuth(scheme='Bearer')

@basicAuth.verify_password
def verify_password(username, password):
    #encryptor = Encryptor()
    #username = encryptor.desencryptMsg(username)
    #password = encryptor.desencryptMsg(password)
  
    res = requests.get("http://34.82.167.68/car/verify?licensePlate="+username+"&sensorId="+password, timeout=3)
    if res.text == "true":
        return True
    else:
        return False
    
@tokenAuth.verify_token
def verify_token(token):
    s = Serializer(app.config['SECRET_KEY'])
    try:
        data = s.loads(token)
    except SignatureExpired:
        return False # valid token, but expired
    except BadSignature:
        return False # invalid token
    return True

def generate_auth_token(expiration = 600):
    s = Serializer(app.config['SECRET_KEY'], expires_in = expiration)
    return s.dumps({ 'id': '123' })

@apiRoute.route('/api/token')
@basicAuth.login_required
def get_auth_token():
    token = generate_auth_token()
    return jsonify({ 'token': token.decode('ascii') })

@apiRoute.route('/api/datalake/sensors', methods=['GET', 'POST'])
@tokenAuth.login_required
def datalakeSensors():
    if request.method == "GET":
        return "online"
    elif request.method == "POST":
        #Send to AlertCloud
        try:
            requests.post(ALERTCLOUD, json=request.json, timeout=3)
        except:
            pass
        #Sendo to Datalake
        workers.queue(jobs.sensors, SENSORS_HOST, request.json)
        return Response(status=200)
    else:
        return Response(status=404)

@apiRoute.route('/api/datalake/raspberry', methods=['GET', 'POST'])
@tokenAuth.login_required
def raspberry():
    if request.method == "GET":
        return "online"
    elif request.method == "POST":
        workers.queue(jobs.raspberry, BOOT_HOST, request.json)
        return Response(status=200)
    else:
        return Response(status=404)

@apiRoute.route('/api/backend/alert', methods=['GET', 'POST'])
@tokenAuth.login_required
def backend():
    if request.method == "GET":
        return "online"
    elif request.method == "POST":
        try:
            cleanHeaders = {"licensePlate": request.headers["licensePlate"]}
            requests.post(BACKEND, json=request.json, headers=cleanHeaders, timeout=3)
            return Response(status=200)
        except:
            return Response(status=404)
    else:
        return Response(status=404)
