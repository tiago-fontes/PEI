#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import os

import redis
from rq import Worker, Queue, Connection, Retry

class Workers:
    def __init__(self):
        self.listen = ['default', 'connError']
        #self.redis_url = os.getenv('REDISTOGO_URL', 'redis://0.0.0.0:6379')
        #self.redis_url = 'redis://0.0.0.0:6379'
        #self.redis_url = os.getenv('REDIS_URL', 'redis://127.0.0.1:6379')
        #self.redis_url = 'redis://127.0.0.1:6379'
        self.host="redis"
        self.port=6379
        self.conn = None
        self.q = None
        self.connect()

    def connect(self):
        #self.conn = redis.from_url(self.redis_url)
        self.conn = redis.Redis(host=self.host, port=self.port, db=0)
        self.q = Queue(connection=self.conn)

    def queue(self, func, host, data):
        #self.q.enqueue(func, host, data, retry=Retry(max=3, interval=[10, 30, 60]))
        #No retries because if it fails it is enqueue in the connError queue by the job, it would duplicate the event
        self.q.enqueue(func, host, data)


    #Note: moved this to the worker.py file, run as a service daemon at boot time. Here conflicted with the flask thread
    def startWorker(self):
        with Connection(self.conn):
            worker = Worker(map(Queue, self.listen))
            worker.work(burst=True)

    def countWorkers(self):
        workers = Worker.all(connection=self.conn)
        count = len(workers)
        return count