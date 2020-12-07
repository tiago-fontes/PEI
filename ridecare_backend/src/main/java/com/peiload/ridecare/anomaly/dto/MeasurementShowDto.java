package com.peiload.ridecare.anomaly.dto;

import com.peiload.ridecare.anomaly.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Builder
@AllArgsConstructor
@Data
public class MeasurementShowDto {

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

    public MeasurementShowDto(Measurement m) {
        this.classification = m.getClassification();
        this.date = m.getDate();
        this.longitude = m.getLongitude();
        this.latitude = m.getLatitude();
        this.pm25 = m.getPm25();
        this.pm10 = m.getPm10();
        this.temperature = m.getTemperature();
        this.gas = m.getGas();
        this.humidity = m.getHumidity();
        this.pressure = m.getPressure();
        this.altitude = m.getAltitude();
    }
}
