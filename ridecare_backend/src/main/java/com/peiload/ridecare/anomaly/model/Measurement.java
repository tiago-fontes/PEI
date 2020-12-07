package com.peiload.ridecare.anomaly.model;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
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
@Table(name="measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Anomaly anomaly;

    //private String classification;


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


    public Measurement(MeasurementSetDto dto, Anomaly anomaly) {
        this.anomaly = anomaly;
        this.date = dto.getDate();
        this.longitude = dto.getLongitude();
        this.latitude = dto.getLatitude();
        this.pm25 = dto.getPm25();
        this.pm10 = dto.getPm10();
        this.temperature = dto.getTemperature();
        this.gas = dto.getGas();
        this.humidity = dto.getHumidity();
        this.pressure = dto.getPressure();
        this.altitude = dto.getAltitude();
    }
}
