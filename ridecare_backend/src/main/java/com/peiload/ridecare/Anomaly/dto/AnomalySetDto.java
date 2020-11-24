package com.peiload.ridecare.Anomaly.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AnomalySetDto {
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

    private Boolean viewed;
}
