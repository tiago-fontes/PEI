#!/bin/bash

docker build -t ridecare-datalake .

docker run -d -e WEB2PY_ADMIN=Pa55word -p 80:80 --name datalake-container ridecare-datalake