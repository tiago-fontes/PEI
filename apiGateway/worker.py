#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import redis, os
from rq import Worker, Queue, Connection

listen = ['default', 'connError']
redis_url = os.getenv('REDISTOGO_URL', 'redis://127.0.0.1:6379')
conn = redis.from_url(redis_url)

if __name__ == '__main__':
    with Connection(conn):
        worker = Worker(list(map(Queue, listen)))
        worker.work()
        #burst = corre todos os jobs e morre / false = fica sempre à escuta de novos
        #worker.work(burst=True)
