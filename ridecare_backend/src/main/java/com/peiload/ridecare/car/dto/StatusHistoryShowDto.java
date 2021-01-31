package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.car.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class StatusHistoryShowDto {
    private CarStatus status;
    private Date date;

    public StatusHistoryShowDto(CarStatus status) {
        this.status = status;
        this.date = new Date();
    }
}
