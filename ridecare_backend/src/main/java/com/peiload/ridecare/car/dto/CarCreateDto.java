package com.peiload.ridecare.car.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CarCreateDto {
    private Integer sensorId;
    @NotNull
    @NotEmpty
    private String licensePlate;
    private String image;
    private String brand;
    private String model;
    private Integer year;
    private Integer numberOfDoors;
    private Integer numberOfSeats;
    private String transmission;
    private String fuel;
}
