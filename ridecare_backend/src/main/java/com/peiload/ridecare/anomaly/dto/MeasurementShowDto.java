package com.peiload.ridecare.anomaly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peiload.ridecare.anomaly.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Builder
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurementShowDto {


    private Date timeValue;
    private String carLocation;

    private Float pm25;
    private Float pm10;

    private Float temperature;
    private Float gas;
    private Float humidity;
    private Float pressure;
    private Float altitude;

    public MeasurementShowDto(Measurement m) {
        this.timeValue = m.getDate();
        this.carLocation = m.getCarLocation();
        this.pm25 = m.getPm25();
        this.pm10 = m.getPm10();
        this.temperature = m.getTemperature();
        this.gas = m.getGas();
        this.humidity = m.getHumidity();
        this.pressure = m.getPressure();
        this.altitude = m.getAltitude();
    }
}

