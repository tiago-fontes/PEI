package com.peiload.ridecare.anomaly.model;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    private Anomaly anomaly;
    
    @NotNull
    private Date date;
    private String carLocation;
    //private Float longitude;
    //private Float latitude;


    //TODO: Se estas variáveis não podem ser Null, podem mudar de Float para float.
    @NotNull
    private Float pm25;
    @NotNull
    private Float pm10;

    @NotNull
    private Float temperature;
    @NotNull
    private Float gas;
    @NotNull
    private Float humidity;
    @NotNull
    private Float pressure;
    @NotNull
    private Float altitude;


    public Measurement(MeasurementSetDto dto, Anomaly anomaly) {
        this.anomaly = anomaly;
        this.date = dto.getDate();
        this.carLocation = dto.getCarLocation();
        this.pm25 = dto.getPm25();
        this.pm10 = dto.getPm10();
        this.temperature = dto.getTemperature();
        this.gas = dto.getGas();
        this.humidity = dto.getHumidity();
        this.pressure = dto.getPressure();
        this.altitude = dto.getAltitude();
    }
}
