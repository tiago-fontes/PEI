package com.peiload.ridecare.anomaly.controller;

import com.peiload.ridecare.anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.anomaly.service.AnomalyService;
import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void createAnomaly(@RequestHeader("Authorization") String authorizationToken, @RequestHeader("CarId") int carId, @RequestBody AnomalySetDto anomalySetDto){
        this.anomalyService.createAnomaly(authorizationToken, carId, anomalySetDto);
    }

    @PatchMapping(path="/edit/{anomalyId}")
    public void editAnomaly(@PathVariable int anomalyId, @RequestBody AnomalySetDto anomalySetDto){
        this.anomalyService.editAnomaly(anomalyId, anomalySetDto);
    }
}
