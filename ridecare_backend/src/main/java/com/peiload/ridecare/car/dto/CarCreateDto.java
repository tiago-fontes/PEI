package com.peiload.ridecare.car.dto;

import lombok.Data;

@Data
public class CarCreateDto {
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
