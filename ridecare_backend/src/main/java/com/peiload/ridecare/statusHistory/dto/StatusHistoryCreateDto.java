package com.peiload.ridecare.statusHistory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
public class StatusHistoryCreateDto {
    @NotNull
    @NotEmpty
    private String status;
    @NotNull
    private Date date;
}
