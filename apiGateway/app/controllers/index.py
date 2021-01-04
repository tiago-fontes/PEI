#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

from flask import Blueprint, render_template, g

indexRoute = Blueprint('index', __name__,  template_folder='views')

@indexRoute.route('/')
def index():
    #title = g.teste
    title = "MEI/MIEI"
    return render_template("index.html", title=title)
