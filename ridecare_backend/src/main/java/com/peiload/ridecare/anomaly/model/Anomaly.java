package com.peiload.ridecare.anomaly.model;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.car.model.Car;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="anomaly")
public class Anomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Boolean viewed;

    @NotNull
    private String classification;

    @NotNull
    @ManyToOne
    private Car car;

    @OneToMany(mappedBy="anomaly")
    private List<Measurement> measurements;

    public Anomaly(MeasurementSetDto anomaly, Car car) {
        this.classification = anomaly.getClassification();
        this.car = car;
        this.viewed = false;
    }
}
