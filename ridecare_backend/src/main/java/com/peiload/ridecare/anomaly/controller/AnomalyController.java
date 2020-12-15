package com.peiload.ridecare.anomaly.controller;

import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.dto.MeasurementShowDto;
import com.peiload.ridecare.anomaly.model.Measurement;
import com.peiload.ridecare.anomaly.service.AnomalyService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@RestController
@RequestMapping("/anomaly")
@Api(tags = "AnomalyController")

public class AnomalyController {
    private final AnomalyService anomalyService;

    public AnomalyController(AnomalyService anomalyService){
        this.anomalyService = anomalyService;
    }

    @GetMapping(path="/all")
    public List<AnomalyShowDto> getAllAnomalies(){
        return this.anomalyService.getAllAnomalies();
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnomaly(@RequestHeader("Authorization") String authorizationToken, @RequestHeader("CarId") int carId, @RequestBody MeasurementSetDto measurementSetDto){
        this.anomalyService.createAnomaly(authorizationToken, carId, measurementSetDto);
    }

    //TODO mudar esta função
    @PatchMapping(path="/{anomalyId}/viewed")
    public void setAnomalyAsViewed(@PathVariable int anomalyId){
        this.anomalyService.setAnomalyAsViewed(anomalyId);
    }

    @GetMapping(path="/{anomalyId}/detailed")
    public List<MeasurementShowDto> getMeasurements(@PathVariable int anomalyId, @RequestParam(value = "numberOfMeasurements", required = false, defaultValue = "5") int numberOfMeasurements){
        return this.anomalyService.getMeasurements(anomalyId, numberOfMeasurements);
    }
}
