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
public class NotificationShowDto {
    private int id;
    private String classification;
    private String licensePlate;

    public NotificationShowDto(Anomaly anomaly) {
        this.id = anomaly.getId();
        this.classification = anomaly.getClassification();
        this.licensePlate = anomaly.getCar().getLicensePlate();
    }
}

