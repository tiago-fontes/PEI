package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Data
public class CarShowDto {
    private int id;
    private String licensePlate;
    private String image;
    private String brand;
    private String model;
    private int year;
    private int numberOfDoors;
    private int numberOfSeats;
    private String transmission;
    private String fuel;
    private List<AnomalyShowDto> anomalies;



    public CarShowDto(Car car) {
        this.id = car.getId();
        this.licensePlate = car.getLicensePlate();
        this.image = car.getImage();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.numberOfDoors = car.getNumberOfDoors();
        this.numberOfSeats = car.getNumberOfSeats();
        this.transmission = car.getTransmission();
        this.fuel = car.getFuel();

        if(car.getAnomalies() == null){
            this.anomalies = new ArrayList<>();
        }
        else {
            this.anomalies = car.getAnomalies().stream().map(AnomalyShowDto::new).collect(Collectors.toList());
        }
    }
}
