package com.peiload.ridecare.car.dto;

import lombok.Data;

@Data
public class CarSetDto {
    private String licensePlate;
    private String brand;
    private String model;
    private Integer year;
    private Integer numberOfDoors;
    private Integer numberOfSeats;
    private String transmission;
    private String fuel;
}
