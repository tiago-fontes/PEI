#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import redis, os, daemon, sys
from rq import Worker, Queue, Connection

listen = ['default', 'connError']
#redis_url = os.getenv('REDISTOGO_URL', 'redis://0.0.0.0:6379')
#redis_url = 'redis://0.0.0.0:6379'
#redis_url = os.getenv('REDIS_URL', 'redis://127.0.0.1:6379')
#redis_url = 'redis://127.0.0.1:6379'
#host="127.0.0.1"

#Adapted for Docker: "redis" = container name in the network
host="redis"
port=6379

#conn = redis.from_url(redis_url)
conn = redis.Redis(host=host, port=port, db=0)

def worker(burst=False):
    with Connection(conn):
        worker = Worker(list(map(Queue, listen)))
        worker.work(burst=burst)

def daemonize():
    with daemon.DaemonContext():
        worker(burst=False)

def makeWorker(daemon=False, burst=False):
    if daemon:
        daemonize()
    else:
        worker(burst=burst)

if __name__ == '__main__':
    d = False
    params = sys.argv
    if len(params) > 1:
        if params[1] == "--daemonize":
            d = True

    makeWorker(daemon=d)


