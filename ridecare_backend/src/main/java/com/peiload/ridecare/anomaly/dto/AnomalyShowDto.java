package com.peiload.ridecare.anomaly.dto;

import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Data
public class AnomalyShowDto {
    private int id;

    private List<MeasurementShowDto> measurements;

    private Boolean viewed;

    public AnomalyShowDto(Anomaly anomaly) {
        this.id = anomaly.getId();
        this.measurements = anomaly.getMeasurements().stream().map(MeasurementShowDto::new).collect(Collectors.toList());
        this.viewed = anomaly.getViewed();
    }
}
