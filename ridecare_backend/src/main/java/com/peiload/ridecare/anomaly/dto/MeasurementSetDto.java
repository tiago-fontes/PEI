package com.peiload.ridecare.anomaly.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MeasurementSetDto {
    private String classification;
    private Date date;
    private Float longitude;
    private Float latitude;

    private Float pm25;
    private Float pm10;

    private Float temperature;
    private Float gas;
    private Float humidity;
    private Float pressure;
    private Float altitude;
}