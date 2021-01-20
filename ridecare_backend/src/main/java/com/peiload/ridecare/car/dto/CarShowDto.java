package com.peiload.ridecare.car.dto;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CarShowDto {
    private int id;
    private int sensorId;
    private String statuss;
    private StatusHistoryShowDto status;
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
        this.statuss = car.getStatus();
        this.licensePlate = car.getLicensePlate();
        this.image = car.getImage();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.numberOfDoors = car.getNumberOfDoors();
        this.numberOfSeats = car.getNumberOfSeats();
        this.transmission = car.getTransmission();
        this.fuel = car.getFuel();

        if(car.getAnomalies() != null){
            this.anomalies = car.getAnomalies().stream().map(AnomalyShowDto::new).collect(Collectors.toList());
        }
        if(car.getStatus().contains("off")){
            this.status = new StatusHistoryShowDto(CarStatus.OFFLINE);
        }
        else{
            this.status = new StatusHistoryShowDto(CarStatus.ONLINE);
        }
    }
}
