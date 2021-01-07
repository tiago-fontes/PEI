package com.peiload.ridecare.anomaly.dto;

import com.peiload.ridecare.anomaly.model.Anomaly;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AnomalyShowDto {
    private int id;
    private Boolean viewed;
    private String classification;
    private List<MeasurementShowDto> measurements;

    private String licensePlate;
    private int carId;

    public AnomalyShowDto(Anomaly anomaly) {
        this.id = anomaly.getId();
        this.classification = anomaly.getClassification();
        this.viewed = anomaly.getViewed();
        this.measurements = anomaly.getMeasurements().stream().map(MeasurementShowDto::new).collect(Collectors.toList());

        this.licensePlate = anomaly.getCar().getLicensePlate();
        this.carId = anomaly.getCar().getId();
    }
}
