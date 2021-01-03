FROM ubuntu:18.04

RUN apt-get update -y && \
    apt-get install -y python-pip python-dev


WORKDIR /app

RUN pip install flask

RUN pip install flask-sqlalchemy

RUN apt install sqlite3

COPY . /app

ENTRYPOINT [ "python" ]

EXPOSE 5000

CMD [ "app.py" ]