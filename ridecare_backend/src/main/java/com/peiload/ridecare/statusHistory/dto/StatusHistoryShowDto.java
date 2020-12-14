package com.peiload.ridecare.statusHistory.dto;

import com.peiload.ridecare.statusHistory.model.StatusHistory;
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
    private String status;
    private Date date;

    public StatusHistoryShowDto(StatusHistory statusHistory) {
        this.status = statusHistory.getStatus();
        this.date = statusHistory.getDate();
    }
}
