package com.peiload.ridecare.Anomaly.model;

import com.peiload.ridecare.Anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="anomaly")
public class Anomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Car car;

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


    public Anomaly(AnomalySetDto anomaly, Car car) {
        this.car = car;

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

        this.viewed = anomaly.getViewed();
    }
}
