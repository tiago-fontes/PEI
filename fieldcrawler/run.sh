#!/bin/bash

apt-get update

apt install redis-server

pip3 install -r requirements.txt

cp fieldcrawler.service /lib/systemd/sysytem/fieldcrawler.service
cp pei-worker.service /lib/systemd/sysytem/pei-worker.service

chmod 644 /lib/systemd/sysytem/fieldcrawler.service
chmod 644 /lib/systemd/sysytem/pei-worker.service

chmod +x main.py
chmod +x worker.py

systemctl daemon-reload
systemctl enable fieldcrawler.service
systemctl enable pei-worker.service