package com.peiload.ridecare.Anomaly.dto;

import com.peiload.ridecare.Anomaly.model.Anomaly;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@AllArgsConstructor
@Data
public class AnomalyShowDto {
    private int id;

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

    public AnomalyShowDto(Anomaly anomaly) {
        this.id = anomaly.getId();
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
