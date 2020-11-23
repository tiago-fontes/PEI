package com.peiload.ridecare.Anomaly.dto;

import com.peiload.ridecare.Anomaly.model.Anomaly;
import com.peiload.ridecare.car.model.Car;

import javax.persistence.ManyToOne;
import java.util.Date;

public class AnomalyShowDto {
    private int id;

    @ManyToOne
    private Car car;

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

    public AnomalyShowDto(Anomaly anomaly) {
        this.id = anomaly.getId();
        this.car = anomaly.getCar();
        this.classification = anomaly.getClassification();
        this.date = anomaly.getDate();
        this.longitude = anomaly.getLongitude();
        this.latitude = anomaly.getLatitude();
        this.pm25 = anomaly.getPm25();
        this.pm10 = anomaly.getPm10();
        this.temperature = anomaly.getTemperature();
        this.gas = anomaly.getGas();
        this.humidity = anomaly.getHumidity();
        this.pressure = anomaly.getPressure();
        this.altitude = anomaly.getAltitude();
    }
}
