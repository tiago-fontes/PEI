# -*- coding: utf-8 -*-

db.define_table('sensors',
                Field('carId', 'string'),
                Field('carLocation', 'string'),
                Field('timeValue', 'datetime'),
                #sensor1
                Field('pm25', 'double'),
                Field('pm10', 'double'),
                #sensor2
                Field('temperature', 'double'),
                Field('gas', 'integer'),
                Field('humidity', 'double'),
                Field('pressure', 'double'),
                Field('altitude', 'double'),
                #metainfo
                #Field('tags', 'string', default=''),
                #Field('classification', 'string', default='0')
               )
