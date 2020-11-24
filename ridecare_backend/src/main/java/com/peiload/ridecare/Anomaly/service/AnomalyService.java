package com.peiload.ridecare.Anomaly.service;

import com.peiload.ridecare.Anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.Anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.Anomaly.model.Anomaly;
import com.peiload.ridecare.Anomaly.repository.AnomalyRepository;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.service.CarService;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnomalyService {

    private final AnomalyRepository anomalyRepository;
    private final JwtTokenUtil jtu;
    private final UserService userService;
    private final CarService carService;



    public AnomalyService(AnomalyRepository anomalyRepository, JwtTokenUtil jtu, UserService userService, CarService carService) {
        this.anomalyRepository = anomalyRepository;
        this.jtu = jtu;
        this.userService = userService;
        this.carService = carService;
    }

    public List<AnomalyShowDto> getAllAnomalies() {
        List<AnomalyShowDto> allAnomalies = new ArrayList<>();
        this.anomalyRepository.findAll().forEach(anomaly -> allAnomalies.add(new AnomalyShowDto(anomaly)));
        return allAnomalies;
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
        else if(!(car.getUser().getEmail().equals(email))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to view belongs to another user");
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's no car with this id");
        }
    }

    public void createAnomaly(String authorizationToken, int carId, AnomalySetDto anomalySetDto) {
        Car car = this.carService.findById(carId);
        //String email = jtu.getEmailFromAuthorizationString(authorizationToken);

        //if(car.get().getUser().getEmail().equals(email)){
        Anomaly anomaly = new Anomaly(anomalySetDto, car);
        this.anomalyRepository.save(anomaly);
        //}
        //else {
        //    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There is no car");
        //}
    }

    public void editAnomaly(int anomalyId, AnomalySetDto anomalySetDto) {
        Optional<Anomaly> existingAnomaly = this.anomalyRepository.findById(anomalyId);
        //String email = jtu.getEmailFromAuthorizationString(authorizationToken);

        if(existingAnomaly.isPresent()){ //&& car.get().getUser().getEmail().equals(email)){
            Anomaly anomaly = existingAnomaly.get();
            updateAnomaly(anomaly, anomalySetDto);
            this.anomalyRepository.save(existingAnomaly.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There is no car");
        }
    }

    private void updateAnomaly(Anomaly anomaly, AnomalySetDto anomalySetDto){
        if(anomalySetDto.getClassification() != null){
            anomaly.setClassification(anomalySetDto.getClassification());
        }
        if(anomalySetDto.getDate() != null){
            anomaly.setDate(anomalySetDto.getDate());
        }
        if(anomalySetDto.getLongitude() != null){
            anomaly.setLongitude(anomalySetDto.getLongitude());
        }
        if(anomalySetDto.getLatitude() != null){
            anomaly.setLatitude(anomalySetDto.getLatitude());
        }

        if(anomalySetDto.getPm25() != null){
            anomaly.setPm25(anomalySetDto.getPm25());
        }
        if(anomalySetDto.getPm10() != null){
            anomaly.setPm10(anomalySetDto.getPm10());
        }

        if(anomalySetDto.getTemperature() != null){
            anomaly.setTemperature(anomalySetDto.getTemperature());
        }
        if(anomalySetDto.getGas() != null){
            anomaly.setGas(anomalySetDto.getGas());
        }
        if(anomalySetDto.getHumidity() != null){
            anomaly.setHumidity(anomalySetDto.getHumidity());
        }
        if(anomalySetDto.getPressure() != null){
            anomaly.setPressure(anomalySetDto.getPressure());
        }
        if(anomalySetDto.getAltitude() != null){
            anomaly.setAltitude(anomalySetDto.getAltitude());
        }

        if(anomalySetDto.getViewed() != null){
            anomaly.setViewed(anomalySetDto.getViewed());
        }
    }

}
