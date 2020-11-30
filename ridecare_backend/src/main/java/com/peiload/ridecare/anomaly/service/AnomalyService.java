package com.peiload.ridecare.anomaly.service;

import com.peiload.ridecare.anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.repository.AnomalyRepository;
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
