package com.peiload.ridecare.Anomaly.controller;

import com.peiload.ridecare.Anomaly.dto.AnomalySetDto;
import com.peiload.ridecare.Anomaly.service.AnomalyService;
import com.peiload.ridecare.Anomaly.dto.AnomalyShowDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anomaly")
@Api(tags = "AnomalyController")

public class AnomalyController {
    private AnomalyService anomalyService;

    public AnomalyController(AnomalyService anomalyService){
        this.anomalyService = anomalyService;
    }

    @GetMapping(path="/all")
    public List<AnomalyShowDto> getAllAnomalies(){
        return this.anomalyService.getAllAnomalies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnomaly(@RequestHeader("Authorization") String authorizationToken, @RequestBody AnomalySetDto anomalySetDto){
        this.anomalyService.createAnomaly(authorizationToken, anomalySetDto);
    }
}
