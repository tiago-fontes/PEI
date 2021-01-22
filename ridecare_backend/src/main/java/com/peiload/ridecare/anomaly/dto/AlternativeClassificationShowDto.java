package com.peiload.ridecare.anomaly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlternativeClassificationShowDto {
    private String algorithm;
    private int classification;
}
