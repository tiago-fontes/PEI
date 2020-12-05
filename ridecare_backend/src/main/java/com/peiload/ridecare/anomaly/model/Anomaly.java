package com.peiload.ridecare.anomaly.model;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    private Boolean viewed;

    @ManyToOne
    private Car car;

    @OneToMany(mappedBy="anomaly")
    private List<Measurement> measurements;

    public Anomaly(MeasurementSetDto anomaly, Car car) {
        this.car = car;
        this.viewed = false;
    }
}
