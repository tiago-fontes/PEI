package com.peiload.ridecare.car.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarEditDto {
    private String licencePlate;
    private String image;
    private String brand;
    private String model;
    private Integer year;
    private Integer numberOfDoors;
    private Integer numberOfSeats;
    private String transmission;
    private String fuel;
}
