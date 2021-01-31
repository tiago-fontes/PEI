FROM python:3.8

# set the working directory in the container
WORKDIR /app

# copy the dependencies file to the working directory
COPY requirements.txt .

# copy the script file 
COPY SQLite.sh .

# install dependencies
RUN pip install -r requirements.txt

RUN chmod 777 SQLite.sh

RUN sh SQLite.sh

# copy the content of the local src directory to the working directory
COPY . /app


# command to run on container start
CMD [ "python", "./app.py" ]
