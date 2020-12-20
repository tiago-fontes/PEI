package com.peiload.ridecare.anomaly.controller;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.dto.DetailedAnomalyShowDto;
import com.peiload.ridecare.anomaly.dto.MeasurementSetDto;
import com.peiload.ridecare.anomaly.service.AnomalyService;
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

@RestController
@RequestMapping("/anomaly")
@Api(tags = "AnomalyController")

public class AnomalyController {
    private final AnomalyService anomalyService;
    private final SimpMessagingTemplate webSocket;

    public AnomalyController(AnomalyService anomalyService, SimpMessagingTemplate webSocket){
        this.anomalyService = anomalyService;
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

    //TODO: criar função para devolver lista de anomalias entre 2 datas
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnomaly(@RequestHeader("carId") int carId, @RequestBody MeasurementSetDto measurementSetDto){
        this.anomalyService.createAnomaly(carId, measurementSetDto);
        webSocket.convertAndSend("/topic/notification", new String("Notification Sent!"));
    }

    //TODO mudar esta função, temos que ver como vai ser para a anomalia ser marcada como vista (botão?)
    @PatchMapping(path="/{anomalyId}/viewed")
    public void setAnomalyAsViewed(@PathVariable int anomalyId){
        this.anomalyService.setAnomalyAsViewed(anomalyId);
    }

    @GetMapping(path="/{anomalyId}/detailed")
    public DetailedAnomalyShowDto getMeasurements(@PathVariable int anomalyId, @RequestParam(value = "numberOfMeasurements", required = false, defaultValue = "5") int numberOfMeasurements){
        return this.anomalyService.getDetailedAnomaly(anomalyId, numberOfMeasurements);
    }
}
