package com.peiload.ridecare.car.controller;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.dto.StatusShowDto;
import com.peiload.ridecare.car.service.CarService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@Api(tags = "CarController")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping
    public List<CarShowDto> getUserCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getUserCars(authorizationToken);
    }

    @GetMapping(path = "/{carId}")
    public CarShowDto getCarById(@RequestHeader("Authorization") String authorizationToken, @PathVariable int carId){
        return this.carService.getCarById(authorizationToken, carId);
    }

    /*@GetMapping(path="/online")
    public List<CarShowDto> getOnlineCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getOnlineCars(authorizationToken);
    }

    @GetMapping(path="/offline")
    public List<CarShowDto> getOfflineCars(@RequestHeader("Authorization") String authorizationToken){
        return this.carService.getOfflineCars(authorizationToken);
    }*/

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

    @GetMapping(path="/verify")
    public Boolean verify(@RequestParam("licensePlate") String licensePlate, @RequestParam("sensorId") int sensorId){
        return this.carService.verify(licensePlate, sensorId);
    }


}