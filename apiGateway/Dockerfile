FROM tiangolo/uwsgi-nginx-flask:python3.8

COPY requirements.txt ./

RUN pip install --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

COPY ./app /app

#WORKDIR /app
#RUN chmod +x worker.py
#RUN python3 worker.py --daemonize
#RUN python3 worker.py --daemonize
