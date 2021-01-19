#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

"""
Monitoring system for the installed sensors
"""

__author__ = "MEI/MIEI 2019/2021 - PEILOAD"
__email__ = "direcao@di.uminho.pt"
__version__ = "0.1.0"
__license__ = "MIT"

import os, importlib

import rq_dashboard

from worker import makeWorker
from rqWorker import Workers

from flask import Flask, g
from flask_cors import CORS

from controllers import *

app = Flask(__name__, template_folder='views')
CORS(app)
app.config.from_object('config')
app.secret_key = app.config['SECRET_KEY']
app.debug = True

#Load atuomatically all routers from the controllers package
controllers = os.path.join(os.getcwd(), "controllers")
for filename in os.listdir(controllers):
    if filename.endswith(".py") and filename != "__init__.py":
        fName = filename.replace(".py","")
        package = importlib.import_module("controllers."+fName)
        module = getattr(package, fName+"Route")
        app.register_blueprint(module)
        

# Rq-Dashboard
app.config.from_object(rq_dashboard.default_settings)
app.config["RQ_DASHBOARD_REDIS_URL"] = "redis://redis:6379"
app.register_blueprint(rq_dashboard.blueprint, url_prefix="/rq")


# Create Workers
#workers = Workers()
#nWorkers = workers.countWorkers()
#if nWorkers == 0:
#    d = makeWorker(daemon=True, burst=False)

#g.state = 'true'
## Global variables for all pages
#@app.context_processor
#def global_data():
    #g.state = 'false'
    #lang = request.args.get("lang", default="pt", type=None)
    #if lang not in ["pt", "en"]:
    #    lang = "pt"
    #return dict(lang=lang)

## Global headersas
#@app.after_request
#def add_header(response):
    #response.headers['Cache-Control'] = 'no-store'
    #return response
