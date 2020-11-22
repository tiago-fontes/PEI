package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CarShowDto {
    private int id;
    private String licensePlate;
    private String brand;
    private String model;
    private int year;
    private int numberOfDoors;
    private int numberOfSeats;
    private String transmission;
    private String fuel;


    public CarShowDto(Car car) {
        this.id = car.getId();
        this.licensePlate = car.getLicensePlate();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.numberOfDoors = car.getNumberOfDoors();
        this.numberOfSeats = car.getNumberOfSeats();
        this.transmission = car.getTransmission();
        this.fuel = car.getFuel();
    }
}
