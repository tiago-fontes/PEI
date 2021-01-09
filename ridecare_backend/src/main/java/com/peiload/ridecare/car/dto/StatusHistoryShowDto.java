package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.car.model.CarStatus;
import com.peiload.ridecare.car.model.StatusHistory;
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

    public StatusHistoryShowDto(StatusHistory statusHistory) {
        this.status = statusHistory.getStatus();
        this.date = statusHistory.getDate();
    }
}
