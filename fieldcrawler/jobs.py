#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests, os, redis
from rq import Connection, get_current_job, requeue_job, Queue

def sensors(host, dataDict):

    try:
        res = requests.post(host, json=dataDict, timeout=3)

        if res.status_code != 200:
            raise Exception('Response not 200. Res: ' + str(res.status_code) )
        #print(res.status_code)
    #if no connection put back on queue
    except:
        redis_url = os.getenv('REDISTOGO_URL', 'redis://127.0.0.1:6379')
        conn = redis.from_url(redis_url)
        q = Queue("connError", connection=conn)

        job = get_current_job()
        q.enqueue_job(job)

def alertCloud(host, data):
    try:
        dataDict = data[0]
        headers = data[1]
        res = requests.post(host, json=dataDict, headers=headers, timeout=3)
        #with open("/home/pi/pei/fieldcrawler/welcome.txt", 'w+') as file:
        #    file.write(res.text)
        if res.status_code != 200:
            raise Exception('Response not 200. Res: ' + str(res.status_code) )
    except:
        redis_url = os.getenv('REDISTOGO_URL', 'redis://127.0.0.1:6379')
        conn = redis.from_url(redis_url)
        q = Queue("connError", connection=conn)

        job = get_current_job()
        q.enqueue_job(job)
