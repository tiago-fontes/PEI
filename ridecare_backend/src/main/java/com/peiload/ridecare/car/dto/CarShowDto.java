package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CarShowDto {
    private int id;
    private int sensorId;
    private CarStatus status;
    private String licensePlate;
    private String image;
    private String brand;
    private String model;
    private int year;
    private int numberOfDoors;
    private int numberOfSeats;
    private String transmission;
    private String fuel;

    private Float latitude;
    private Float longitude;

    private List<AnomalyShowDto> anomalies;


    public CarShowDto(Car car) {
        this.id = car.getId();
        this.sensorId = car.getSensorId();
        this.status = car.getStatus();
        this.licensePlate = car.getLicensePlate();
        this.image = car.getImage();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.numberOfDoors = car.getNumberOfDoors();
        this.numberOfSeats = car.getNumberOfSeats();
        this.transmission = car.getTransmission();
        this.fuel = car.getFuel();

        this.latitude = car.getLatitude();
        this.longitude = car.getLongitude();

        if(car.getAnomalies() == null){
            this.anomalies = new ArrayList<>();
        }
        else {
            this.anomalies = car.getAnomalies().stream().map(AnomalyShowDto::new).collect(Collectors.toList());
        }
    }
}
