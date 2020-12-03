#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
API router
"""

#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import requests
from flask import Blueprint, request, Response, jsonify
from flask_httpauth import HTTPTokenAuth, HTTPBasicAuth

#RQ
from rq import Queue
from rqWorker import conn



from itsdangerous import (TimedJSONWebSignatureSerializer
                          as Serializer, BadSignature, SignatureExpired)



q = Queue(connection=conn)

apiRoute = Blueprint('api', __name__,  template_folder='views')

basicAuth = HTTPBasicAuth()
tokenAuth = HTTPTokenAuth(scheme='Bearer')

DATALAKE_HOST = "http://cehum.ilch.uminho.pt/datalake/api/sensors"

users = {
    "VP-35-44" : "VP-35-44"   
}


@basicAuth.verify_password
def verify_password(username, password):
    if username in users:
        return users.get(username) == password
    else:
        return False
    

@tokenAuth.verify_token
def verify_token(token):
    s = Serializer('rjvZhLKKCC5crnH6AU9m6Q')
    try:
            data = s.loads(token)
    except SignatureExpired:
            return False # valid token, but expired
    except BadSignature:
            return False # invalid token
    return True    


def generate_auth_token(expiration = 600):
        s = Serializer('rjvZhLKKCC5crnH6AU9m6Q', expires_in = expiration)
        return s.dumps({ 'id': '123' })




@apiRoute.route('/api/token')
@basicAuth.login_required
def get_auth_token():
    token = generate_auth_token()
    return jsonify({ 'token': token.decode('ascii') })





@apiRoute.route('/api/sensors', methods=['GET', 'POST'])
@tokenAuth.login_required
def datalakeSensor():
    if request.method == "GET":
        #return jsonify("https://cehum.ilch.uminho.pt/ridecare/json.load")
        pass
    elif request.method == "POST":
        #request.json / request.data / request.get_json(force=True)
        #q.enqueue(sensor1, request.json)
        #request.form['name'] ---> form["sensor"] --> sensor 1 or 2
        dataDict = request.get_json(force=True)
        res = requests.post(DATALAKE_HOST, json=dataDict)
        print('response from server:'+res.text)
        return jsonify(success=True)
        #dictFromResponse = res.json()
        #print(dictFromResponse)
    else:
        return Response(status=404)


#@apiRoute.route('/api/dashboard', methods=['GET', 'POST'])
#def dashboard():
#    if request.method == "GET":
#        pass
#    if request.method == "POST":
#        pass

#@apiRoute.route('/api/raspberry', methods=['GET', 'POST'])
#def raspberry():
#    if request.method == "GET":
#        pass
#    if request.method == "POST":
#        pass

#@apiRoute.route('/api/update', methods=['GET', 'POST'])
#def update():
#    if request.method == "GET":
#        pass
#    if request.method == "POST":
#        pass

#@apiRoute.route('/api/sensor1/<string:action>')
#def sensor1Action(action=None):
#    pass
