package com.peiload.ridecare.car.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class StatusHistoryRequestDto {
    private Date initialDate;
    private Date finalDate;

}
