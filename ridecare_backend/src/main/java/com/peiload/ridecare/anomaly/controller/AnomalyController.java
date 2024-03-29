package com.peiload.ridecare.anomaly.controller;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.dto.DetailedAnomalyShowDto;
import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.anomaly.dto.NotificationShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.service.AnomalyService;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.car.service.CarService;
import com.peiload.ridecare.user.model.User;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anomaly")
@Api(tags = "AnomalyController")

public class AnomalyController {
    private final AnomalyService anomalyService;
    private final CarService carService;
    private final SimpMessagingTemplate webSocket;
    private final CarRepository carRepository;

    public AnomalyController(AnomalyService anomalyService, CarService carService, CarRepository carRepository,SimpMessagingTemplate webSocket){
        this.anomalyService = anomalyService;
        this.carService = carService;
        this.carRepository = carRepository;
        this.webSocket = webSocket;
    }

    @GetMapping(path="/all")
    public List<AnomalyShowDto> getAllAnomalies(){
        return this.anomalyService.getAllAnomalies();
    }

    @GetMapping(path="/user/all")
    public List<AnomalyShowDto> getAllUserAnomalies(@RequestHeader("Authorization") String authorizationToken){
        return this.anomalyService.getAllUserAnomalies(authorizationToken);
    }

    //Gets all the non-viewed anomalies from all the cars from one user
    @GetMapping(path="/user/latest")
    public List<AnomalyShowDto> getLatestAnomaliesUser(@RequestHeader("Authorization") String authorizationToken){
        return this.anomalyService.getLatestAnomaliesUser(authorizationToken);
    }

    //Get all the non-viewed anomalies from one car
    @GetMapping(path="/car/{carId}/latest")
    public List<AnomalyShowDto> getLatestAnomaliesCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable int carId){
        return this.anomalyService.getLatestAnomaliesCar(authorizationToken, carId);
    }

    @GetMapping(path="/date/")
    public List<AnomalyShowDto> getAnomaliesByDate(@RequestHeader("Authorization") String authorizationToken, @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date){
        return this.anomalyService.getAnomaliesByDate(authorizationToken, date);
    }

    @GetMapping(path="/history")
    public List<AnomalyShowDto> getAnomaliesBetweenDates(@RequestHeader("Authorization") String authorizationToken,
                                                         @RequestParam("initialDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date initialDate,
                                                         @RequestParam("finalDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date finalDate){
        return this.anomalyService.getAnomaliesBetweenDates(authorizationToken, initialDate, finalDate);
    }

    @PostMapping(path="/create")
    @ResponseStatus(HttpStatus.OK)
    public void createAnomaly(@RequestHeader("licensePlate") String licensePlate, @RequestBody MeasurementSetDto measurementSetDto){
        int carId = this.carRepository.findByLicensePlate(licensePlate).get().getId();
        Optional<Anomaly> asd = this.anomalyService.createAnomaly(carId, measurementSetDto);
        if(asd.isPresent()){
            User user = this.carService.findUserByCarId(carId);
            NotificationShowDto notification = new NotificationShowDto(asd.get());
            webSocket.convertAndSend("/queue/" + user.getCompanyName(), notification);
        }

    }

    @PatchMapping(path="/{anomalyId}/viewed")
    public void setAnomalyAsViewed(@PathVariable int anomalyId){
        this.anomalyService.setAnomalyAsViewed(anomalyId);
    }

    @GetMapping(path="/{anomalyId}/detailed")
    public DetailedAnomalyShowDto getDetailedAnomaly(@PathVariable int anomalyId, @RequestParam(value = "numberOfMeasurements", required = false, defaultValue = "5") int numberOfMeasurements){
        return this.anomalyService.getDetailedAnomaly(anomalyId, numberOfMeasurements);
    }
}
