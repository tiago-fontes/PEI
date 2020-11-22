# RideCare Backend

## Pre-Requisites

- Maven;

    - Installation for Linux

        - ```apt-cache search maven```
    
        - ```sudo apt-get install maven```
    
- IntelliJ IDEA or another IDE;
- Docker-Compose;

---

## Run Database
    
 On the root directory, where the docker-compose.yml file is, run the following command:

 ```docker-compose up```

## Run Application

#### Using Maven

1. ```mvn clean install```
2. ```java -jar target/ridecare(...).jar```

#### Using IntelliJ

1. Open the project on IntelliJ.
2. Run RidecareApplication.

---
## Swagger UI

[http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)
