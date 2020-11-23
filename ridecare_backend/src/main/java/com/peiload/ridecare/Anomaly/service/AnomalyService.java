package com.peiload.ridecare.Anomaly.service;

import com.peiload.ridecare.Anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.Anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.Anomaly.model.Anomaly;
import com.peiload.ridecare.Anomaly.repository.AnomalyRepository;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnomalyService {

    private final AnomalyRepository anomalyRepository;
    private final CarRepository carRepository;


    public AnomalyService(AnomalyRepository anomalyRepository, CarRepository carRepository) {
        this.anomalyRepository = anomalyRepository;
        this.carRepository = carRepository;
    }

    public List<AnomalyShowDto> getAllAnomalies() {
        List<AnomalyShowDto> allAnomalies = new ArrayList<>();
        this.anomalyRepository.findAll().forEach(anomaly -> allAnomalies.add(new AnomalyShowDto(anomaly)));
        return allAnomalies;
    }

    public void createAnomaly(String authorizationToken, AnomalySetDto anomalySetDto) {


        Optional<Car> car = this.carRepository.findById(anomalySetDto.getCarId());
        if(car.isPresent()){
            Anomaly anomaly = new Anomaly(anomalySetDto);
            this.anomalyRepository.save(anomaly);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There is no car");

        }
        /*

        Optional<Car> existingCar = this. anomalyRepository.findByLicensePlate(carSetDto.getLicensePlate());
        if(!(existingCar.isPresent())){
            String email = jtu.getEmailFromAuthorizationString(authorizationToken);
            User user = this.userService.findByEmail(email);
            Car car = new Car(carSetDto, user);
            this.carRepository.save(car);
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's already a car with this license plate");
        }

         */
    }
}
