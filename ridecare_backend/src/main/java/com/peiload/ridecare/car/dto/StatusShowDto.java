package com.peiload.ridecare.car.dto;

import lombok.Getter;

@Getter
public class StatusShowDto {
    String status;

    public StatusShowDto(String status) {
        this.status = status;
    }
}
