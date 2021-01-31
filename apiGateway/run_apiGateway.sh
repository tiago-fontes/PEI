#!/bin/bash

docker build -t ridecare-apiGateway .

docker run -d -p 80:80 --name apigateway-container ridecare-apiGateway
