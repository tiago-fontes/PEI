# -*- coding: utf-8 -*-

#@auth.requires_membership('admin')
@auth.requires_login()
def index():
    #if request.args(0) == "bme680":
        #response.flash="deu"
        #grid = SQLFORM.grid(db.sensor2, paginate=20, deletable=False, editable=True, create=True, user_signature=False, orderby=~db.sensor2.id)
        #sensor = "bme680"
    #else:
        #grid = SQLFORM.grid(db.sensor1, paginate=20, deletable=False, editable=True, create=True, user_signature=False, orderby=~db.sensor1.id)
        #sensor = "sds011"
    #return locals()
    if request.args(0) == "bootings":
        load = "bootings"
    else:
        load = "sensor"
    return dict(load=load)

def sensor():
    grid = SQLFORM.grid(db.sensors, paginate=20, deletable=True, editable=True, create=True, user_signature=False, orderby=~db.sensors.id)
    load = "sensor"
    return locals()

def bootings():
    grid = SQLFORM.grid(db.raspberries, paginate=20, deletable=True, editable=True, create=True, user_signature=False, orderby=~db.raspberries.id)
    load = "bootings"
    return locals()

def sensor1():
    grid = SQLFORM.grid(db.sensor1, paginate=20, deletable=True, editable=True, create=True, user_signature=False, orderby=~db.sensor1.id)
    sensor = "sds011"
    return locals()

def sensor2():
    grid = SQLFORM.grid(db.sensor2, paginate=20, deletable=True, editable=True, create=True, user_signature=False, orderby=~db.sensor2.id)
    sensor = "bme680"
    return locals()

def sendData():
    #gluon.contrib.simplejson.loads(b)
    pass

def sensors():
    if request.args(0) == "all" or request.args(0) == None:
        form = db().select(db.sensors.id, db.sensors.carId, db.sensors.carLocation, db.sensors.timeValue, db.sensors.pm25, db.sensors.pm10,
                           db.sensors.temperature, db.sensors.gas, db.sensors.humidity, db.sensors.pressure, db.sensors.altitude,
                       orderby=~db.sensors.id)
    elif request.args(0) == "last":
        form = db().select(db.sensors.id, db.sensors.carId, db.sensors.carLocation, db.sensors.timeValue, db.sensors.pm25, db.sensors.pm10,
                           db.sensors.temperature, db.sensors.gas, db.sensors.humidity, db.sensors.pressure, db.sensors.altitude,
                       orderby=~db.sensors.id, limitby=(0,1))
    if request.args(1) == "json":
        return form.as_json()
    elif request.args(1) == "csv":
        #csv = form.as_csv().replace("sensors.","")
        csv = form.as_csv()
        return csv
    else:
        return form.as_json()

def boots():
    if request.args(0) == None:
        data = db( (db.raspberries.id > 0) ).select(orderby=~db.raspberries.id)
    else:
        carId = request.args(0)
        data = db( (db.raspberries.id > 0) & (db.raspberries.carId == carId) ).select(orderby=~db.raspberries.id)
    return data.as_json()

def raspberry():
    import datetime
    carId = request.args(1)
    try:
        if request.args(0) == "status" or request.args(0) == None:
            last2 = db( (db.sensors.id > 0) & (db.sensors.carId == carId) ).select(db.sensors.timeValue, orderby=~db.sensors.id, limitby=(0,2))
            now = datetime.datetime.now()
            #a = datetime.datetime.strptime(last2[0]["timeValue"], '%Y-%m-%d %H:%M:%S')
            #b = datetime.datetime.strptime(last2[1]["timeValue"], '%Y-%m-%d %H:%M:%S')
            #delta = last2[0]["timeValue"] - last2[1]["timeValue"]
            delta = last2[0]["timeValue"] - now
            if delta.seconds <= 5:
                status = {"status": "on"}
            else:
                status = {"status": "off"}
            #return last2[0]["timeValue"]
            return response.json(status)
    except:
        return None

def history():
    import datetime
    carId = request.args(0)
    timestamp=float(request.args(1))
    if request.args(2):
        limit=int(request.args(2))
    else:
        limit=-1
    dTime = datetime.datetime.fromtimestamp(timestamp)
    data = db( (db.sensors.timeValue <= dTime) & (db.sensors.carId == carId) ).select(orderby=~db.sensors.id, limitby=(0,limit))
    return data.as_json()

def recent():
    import datetime
    carId = request.args(0)
    timestamp=float(request.args(1))
    if request.args(2):
        limit=int(request.args(2))
    else:
        limit=-1
    dTime = datetime.datetime.fromtimestamp(timestamp)
    data = db( (db.sensors.timeValue >= dTime) & (db.sensors.carId == carId) ).select(orderby=db.sensors.id, limitby=(0,limit))
    return data.as_json()

def sds011():
    if request.args(0) == "all":
        form = db().select(db.sensor1.id, db.sensor1.carId, db.sensor1.carLocation, db.sensor1.timeValue, db.sensor1.pm25, db.sensor1.pm10,
                       orderby=~db.sensor1.id, )
    elif request.args(0) == "last":
        form = db().select(db.sensor1.id, db.sensor1.carId, db.sensor1.carLocation, db.sensor1.timeValue, db.sensor1.pm25, db.sensor1.pm10,
                       orderby=~db.sensor1.id, limitby=(0,1))
    else:
        form = db().select(db.sensor1.id, db.sensor1.carId, db.sensor1.carLocation, db.sensor1.timeValue, db.sensor1.pm25, db.sensor1.pm10,
                       orderby=~db.sensor1.id)
    if request.args(1) == "json":
        return form.as_json()
    elif request.args(1) == "csv":
        return form.as_csv()
    else:
        return form.as_json()

def bme680():
    if request.args(0) == "all":
        form = db().select(db.sensor2.id, db.sensor2.carId, db.sensor2.carLocation, db.sensor2.timeValue, db.sensor2.temperature, db.sensor2.gas, db.sensor2.humidity, db.sensor2.pressure, db.sensor2.altitude,
                       orderby=~db.sensor2.id)
    elif request.args(0) == "last":
        form = db().select(db.sensor2.id, db.sensor2.carId, db.sensor2.carLocation, db.sensor2.timeValue, db.sensor2.temperature, db.sensor2.gas, db.sensor2.humidity, db.sensor2.pressure, db.sensor2.altitude,
                       orderby=~db.sensor2.id, limitby=(0,1))
    else:
        form = db().select(db.sensor2.id, db.sensor2.carId, db.sensor2.carLocation, db.sensor2.timeValue, db.sensor2.temperature, db.sensor2.gas, db.sensor2.humidity, db.sensor2.pressure, db.sensor2.altitude,
                       orderby=~db.sensor2.id)
    if request.args(1) == "json":
        return form.as_json()
    elif request.args(1) == "csv":
        return form.as_csv()
    else:
        return form.as_json()
    
def json():
    if request.args(0) == "bme680":
        form = db().select(db.sensor2.id, db.sensor2.carId, db.sensor2.carLocation, db.sensor2.timeValue, db.sensor2.temperature, db.sensor2.gas, db.sensor2.humidity, db.sensor2.pressure, db.sensor2.altitude,
                       orderby=~db.sensor2.id)
    else:
        form = db().select(db.sensor1.id, db.sensor1.carId, db.sensor1.carLocation, db.sensor1.timeValue, db.sensor1.pm25, db.sensor1.pm10,
                       orderby=~db.sensor1.id)
    return form.as_json()
    #return json.dumps([[r.id, r.FlowRate] for r in form])

def csv():
    if request.args(0) == "bme680":
        form = db().select(db.sensor2.id, db.sensor2.carId, db.sensor2.carLocation, db.sensor2.timeValue, db.sensor2.temperature, db.sensor2.gas, db.sensor2.humidity, db.sensor2.pressure, db.sensor2.altitude,
                       orderby=~db.sensor2.id)
    else:
        form = db().select(db.sensor1.id, db.sensor1.carId, db.sensor1.carLocation, db.sensor1.timeValue, db.sensor1.pm25, db.sensor1.pm10,
                       orderby=~db.sensor1.id)
    return form.as_csv()
    
@request.restful()
def api():

    def GET(tablename, id):
        if tablename == 'sensors':
            return dict(sensors = db.sensors(id))
        else:
            raise HTTP(400)

    def POST(tablename, **fields):
        if tablename == "sensors":
            return db.sensors.validate_and_insert(**fields)
        elif tablename == "raspberry":
            return db.raspberries.validate_and_insert(**fields)
        else:
            raise HTTP(400)

    #def GET(*args, **vars):
    #    return dict()

    #def POST(*args, **vars):
    #    return dict()

    #def PUT(*args, **vars):
    #    return dict()

    #def DELETE(*args, **vars):
    #    return dict()

    return locals()

# ---- Action for login/register/etc (required for auth) -----
def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/bulk_register
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    also notice there is http://..../[app]/appadmin/manage/auth to allow administrator to manage users
    """
    return dict(form=auth())

# ---- action to server uploaded static content (required) ---
@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)
