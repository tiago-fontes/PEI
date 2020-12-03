#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import os

import redis
from rq import Worker, Queue, Connection

WORKERS = 0

listen = ['high', 'default', 'low']

redis_url = os.getenv('REDISTOGO_URL', 'redis://127.0.0.1:6379')

conn = redis.from_url(redis_url)

def start_worker():
    with Connection(conn):
        worker = Worker(map(Queue, listen))
        worker.work()
        WORKERS += 1

if __name__ == '__main__':
    with Connection(conn):
        worker = Worker(map(Queue, listen))
        worker.work()
