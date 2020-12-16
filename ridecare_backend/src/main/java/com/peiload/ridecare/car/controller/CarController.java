package com.peiload.ridecare.car.controller;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.dto.StatusHistoryRequestDto;
import com.peiload.ridecare.car.dto.StatusHistoryShowDto;
import com.peiload.ridecare.car.model.StatusHistory;
import com.peiload.ridecare.car.service.CarService;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/car")
@Api(tags = "CarController")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping(path="/all")
    public List<CarShowDto> getAllCars(){
        return this.carService.getAllCars();
    }

    @GetMapping
    public List<CarShowDto> getUserCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getUserCars(authorizationToken);
    }

    @GetMapping(path = "/{carId}")
    public CarShowDto getCarById(@RequestHeader("Authorization") String authorizationToken, @PathVariable int carId){
        return this.carService.getCarById(authorizationToken, carId);
    }

    @GetMapping(path="/online")
    public List<CarShowDto> getOnlineCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getOnlineCars(authorizationToken);
    }

    @GetMapping(path="/offline")
    public List<CarShowDto> getOfflineCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getOfflineCars(authorizationToken);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarShowDto createCar(@RequestHeader("Authorization") String authorizationToken, @RequestBody CarCreateDto carCreateDto){
        return this.carService.createCar(authorizationToken, carCreateDto);
    }

    @PatchMapping(path="/{carId}")
    public void editCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable int carId, @RequestBody CarEditDto carEditDto){
        this.carService.editCar(authorizationToken, carId, carEditDto);
    }

    @DeleteMapping(path="/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable int carId){
        this.carService.deleteCar(authorizationToken, carId);
    }

    @GetMapping(path="/{carId}/history")
    public List<StatusHistoryShowDto> getStatusHistoryBetweenDates(@PathVariable int carId,
                                                                   @RequestParam("initialDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date initialDate,
                                                                   @RequestParam("finalDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date finalDate){
        return this.carService.getStatusHistoryBetweenDates(carId, initialDate, finalDate);
    }

    /*@GetMapping(path="/history")
    public List<StatusHistory> getStatusHistory(){

    }*/
}