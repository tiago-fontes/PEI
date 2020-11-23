package com.peiload.ridecare.Anomaly.dto;

import com.peiload.ridecare.car.model.Car;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class AnomalySetDto {
    private int carId;

    private String classification;
    private Date date;
    private float longitude;
    private float latitude;

    private float pm25;
    private float pm10;

    private float temperature;
    private float gas;
    private float humidity;
    private float pressure;
    private float altitude;



}
