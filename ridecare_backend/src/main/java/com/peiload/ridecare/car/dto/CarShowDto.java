package com.peiload.ridecare.car.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CarShowDto {
    private int id;
    private int sensorId;
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

        this.status = getCurrentStatus(this.licensePlate);
    }

    public StatusHistoryShowDto getCurrentStatus(String licensePlate){
        String url = "http://34.105.216.153/datalake/raspberry/status/"+ licensePlate;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        String datalake_status = rateResponse.getBody();
        System.out.println(datalake_status);
        if(datalake_status != null && datalake_status.contains("\"on\"")){
            System.out.println("on");

            return new StatusHistoryShowDto(CarStatus.ONLINE);
        }
        else{
            System.out.println("off");

            return new StatusHistoryShowDto(CarStatus.OFFLINE);
        }

    }
}
