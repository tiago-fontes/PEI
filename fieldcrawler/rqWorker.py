#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os

import redis
from rq import Worker, Queue, Connection, Retry

class Workers:
    def __init__(self):
        self.listen = ['high', 'default', 'low']
        self.redis_url = os.getenv('REDISTOGO_URL', 'redis://127.0.0.1:6379')
        self.conn = None
        self.q = None
        self.connect()

        #if self.countWorkers() == 0:
        #    self.startWorker()

    def connect(self):
        self.conn = redis.from_url(self.redis_url)
        self.q = Queue(connection=self.conn)

    def queue(self, func, host, data):
        #self.q.enqueue(func, host, data, retry=Retry(max=3, interval=[10, 30, 60]))
        self.q.enqueue(func, host, data)
        #self.startWorker()


    #Note: error in the self class. Criei worker.py external que corre em service boot time
    def startWorker(self):
        with Connection(self.conn):
            worker = Worker(map(Queue, self.listen))
            worker.work(burst=True)

    def countWorkers(self):
        #redis = redis.Redis()
        workers = Worker.all(connection=self.conn)
        count = len(workers)
        return count