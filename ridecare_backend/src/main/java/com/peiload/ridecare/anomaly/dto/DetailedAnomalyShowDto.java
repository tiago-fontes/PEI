package com.peiload.ridecare.anomaly.dto;

import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.model.Measurement;
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
public class DetailedAnomalyShowDto {
    private List<MeasurementShowDto> beforeAnomaly;
    private List<MeasurementShowDto> duringAnomaly;
    private List<MeasurementShowDto> afterAnomaly;
    private String classification;
    private String licensePlate;
    private int carId;

    public DetailedAnomalyShowDto(Anomaly anomaly, List<Measurement> beforeAnomaly, List<Measurement> duringAnomaly, List<Measurement> afterAnomaly) {
        this.beforeAnomaly = beforeAnomaly.stream().map(MeasurementShowDto::new).collect(Collectors.toList());
        this.duringAnomaly = duringAnomaly.stream().map(MeasurementShowDto::new).collect(Collectors.toList());
        this.afterAnomaly = afterAnomaly.stream().map(MeasurementShowDto::new).collect(Collectors.toList());
        this.classification = anomaly.getClassification();
        this.licensePlate = anomaly.getCar().getLicensePlate();
        this.carId = anomaly.getCar().getId();
    }
}
