# RideCare Backend

## Pre-Requisites

- Maven;

    - Installation for Linux

        - ```apt-cache search maven```
    
        - ```sudo apt-get install maven```
    
- IntelliJ IDEA or another IDE;
- Docker-Compose;

---
## Generate Jar File

1. ```mvn install -DskipTests```

## Run Database
    
 On the root directory, where the docker-compose.yml file is, run the following command:

 ```docker-compose up mysql```

## Run Application

#### Using Docker Compose

1. ```docker-compose up mysql```
2. ```docker-compose up spring_boot```


#### Using IntelliJ

1. Open the project on IntelliJ.
2. Run RidecareApplication.

---
## Swagger UI

[http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)
