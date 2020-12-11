package com.peiload.ridecare.anomaly.service;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.model.Measurement;
import com.peiload.ridecare.anomaly.repository.AnomalyRepository;
import com.peiload.ridecare.anomaly.repository.MeasurementRepository;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.service.CarService;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnomalyService {

    private final AnomalyRepository anomalyRepository;
    private final MeasurementRepository measurementRepository;
    private final JwtTokenUtil jtu;
    private final UserService userService;
    private final CarService carService;



    public AnomalyService(AnomalyRepository anomalyRepository, MeasurementRepository measurementRepository, JwtTokenUtil jtu, UserService userService, CarService carService) {
        this.anomalyRepository = anomalyRepository;
        this.measurementRepository = measurementRepository;
        this.jtu = jtu;
        this.userService = userService;
        this.carService = carService;
    }

    public List<AnomalyShowDto> getAllAnomalies() {
        return this.anomalyRepository.findAll().stream().map(AnomalyShowDto::new).collect(Collectors.toList());
    }

    public List<AnomalyShowDto> getLatestAnomaliesUser(String authorizationToken) {
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        User user = userService.findByEmail(email);

        List<Car> userCars = user.getCars();

        return anomalyRepository.findAllByViewedAndCarIn(false, userCars);
    }

    public List<AnomalyShowDto> getLatestAnomaliesCar(String authorizationToken, int carId) {
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        Car car = this.carService.findById(carId);

        if(car.getUser().getEmail().equals(email)){
            return anomalyRepository.findAllByViewedAndCar(false, car);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to view belongs to another user");
        }

    }

    public void createAnomaly(String authorizationToken, int carId, MeasurementSetDto measurementSetDto) {
        Car car = this.carService.findById(carId);

        if(car.getAnomalies().isEmpty()){
            Anomaly newAnomaly = new Anomaly(measurementSetDto, car);
            this.anomalyRepository.save(newAnomaly);
            this.measurementRepository.save(new Measurement(measurementSetDto, newAnomaly));
        }
        else{
            List<Anomaly> anomalies = car.getAnomalies();
            Anomaly lastAnomaly = anomalies.get(anomalies.size()-1);

            List<Measurement> measurements = lastAnomaly.getMeasurements();
            Measurement lastMeasurement = measurements.get(measurements.size()-1);

            //If the new measurement exceeds 30 seconds timeout or if the classifications are different, creates a new anomaly
            if(lastMeasurement.getDate().toInstant().plusSeconds(30).isBefore(measurementSetDto.getDate().toInstant())
                    || !(measurementSetDto.getClassification().equals(lastAnomaly.getClassification()))){
                Anomaly newAnomaly = new Anomaly(measurementSetDto, car);
                this.anomalyRepository.save(newAnomaly);
                this.measurementRepository.save(new Measurement(measurementSetDto, newAnomaly));
            }
            else {
                this.measurementRepository.save(new Measurement(measurementSetDto, lastAnomaly));
            }
        }
    }

    public void setAnomalyAsViewed(int anomalyId) {
        Optional<Anomaly> existingAnomaly = this.anomalyRepository.findById(anomalyId);

        if(existingAnomaly.isPresent()){
            Anomaly anomaly = existingAnomaly.get();
            anomaly.setViewed(true);
            this.anomalyRepository.save(anomaly);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Anomaly doesn't exist");
        }
    }
}
