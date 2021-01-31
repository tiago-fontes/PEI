#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests, os, redis
from rq import Connection, get_current_job, requeue_job, Queue

#redis_url = 'redis://0.0.0.0:6379'
#redis_url = os.getenv('REDIS_URL', 'redis://127.0.0.1:6379')
#redis_url = 'redis://127.0.0.1:6379'
host="redis"
port=6379

def sensors(host, data):
    try:
        res = requests.post(host, json=data, timeout=3)
        if res.status_code != 200:
            raise Exception('Response not 200. Res: ' + str(res.status_code) )
    except:#if no connection put back on queue in connError
        #redis_url = os.getenv('REDISTOGO_URL', 'redis://0.0.0.0:6379')
        #conn = redis.from_url(redis_url)
        conn = redis.Redis(host=host, port=port, db=0)
        q = Queue("connError", connection=conn)
        job = get_current_job()
        q.enqueue_job(job)

def raspberry(host, data):
    try:
        res = requests.post(host, json=data, timeout=3)
        if res.status_code != 200:
            raise Exception('Response not 200. Res: ' + str(res.status_code) )
    except:
        #redis_url = os.getenv('REDISTOGO_URL', 'redis://0.0.0.0:6379')
        #conn = redis.from_url(redis_url)
        conn = redis.Redis(host=host, port=port, db=0)
        q = Queue("connError", connection=conn)
        job = get_current_job()
        q.enqueue_job(job)

