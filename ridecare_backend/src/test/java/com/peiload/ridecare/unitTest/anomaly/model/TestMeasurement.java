package com.peiload.ridecare.unitTest.anomaly.model;

import com.peiload.ridecare.anomaly.dto.MeasurementShowDto;
import com.peiload.ridecare.anomaly.model.Measurement;


import java.util.Calendar;
import java.util.Date;

public class TestMeasurement {
    public static Measurement getMeasurement1(){
        return Measurement.builder()
                .id(1)
                .date(new Date(120, Calendar.DECEMBER, 25, 12, 0, 0))
                .carLocation("0 0")
                .pm25(25f)
                .pm10(10f)
                .temperature(11f)
                .gas(0.1f)
                .humidity(0.94f)
                .pressure(0.99f)
                .altitude(112f)
                .build();
    }

    public static MeasurementShowDto getMeasurement1CreateDto1(){
        return MeasurementShowDto.builder()
                .timeValue(new Date(120, Calendar.DECEMBER, 25, 12, 0, 0))
                .carLocation("0 0")
                .pm25(25f)
                .pm10(10f)
                .temperature(11f)
                .gas(0.1f)
                .humidity(0.94f)
                .pressure(0.99f)
                .altitude(112f)
                .build();
    }

    public static Measurement getMeasurement2(){
        return Measurement.builder()
                .id(2)
                .date(new Date(120, Calendar.DECEMBER, 25, 12, 0, 5))
                .carLocation("0 0")
                .pm25(25f)
                .pm10(10f)
                .temperature(11f)
                .gas(0.1f)
                .humidity(0.94f)
                .pressure(0.98f)
                .altitude(112f)
                .build();
    }

    public static Measurement getMeasurement3(){
        return Measurement.builder()
                .id(3)
                .date(new Date(120, Calendar.DECEMBER, 25, 12, 1, 0))
                .carLocation("0 0")
                .pm25(25f)
                .pm10(10f)
                .temperature(11f)
                .gas(0.1f)
                .humidity(0.94f)
                .pressure(0.97f)
                .altitude(112f)
                .build();
    }

    public static Measurement getMeasurement4(){
        return Measurement.builder()
                .id(3)
                .date(new Date(120, Calendar.DECEMBER, 24, 12, 1, 0))
                .carLocation("0 0")
                .pm25(30f)
                .pm10(3f)
                .temperature(0f)
                .gas(3f)
                .humidity(0.50f)
                .pressure(0.90f)
                .altitude(10f)
                .build();
    }

}
