package com.peiload.ridecare.car.controller;

import com.peiload.ridecare.car.dto.CarSetDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.service.CarService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@Api(tags = "CarController")
public class CarController {

    private CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping(path="/all")
    public List<CarShowDto> getAllCars(){
        return this.carService.getAllCars();
    }

    @GetMapping(path="/{company}")
    public List<CarShowDto> getUserCars(@PathVariable String company){
        return this.carService.getUserCars(company);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCar(@RequestHeader("Authorization") String authorizationToken, @RequestBody CarSetDto carSetDto){
        this.carService.createCar(authorizationToken, carSetDto);
    }

    @DeleteMapping(path="/{licensePlate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable String licensePlate){
        this.carService.deleteCar(authorizationToken, licensePlate);
    }

    @PatchMapping(path="/edit/{id}")
    public void editCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable int id, @RequestBody CarSetDto carSetDto){
        this.carService.editCar(authorizationToken, id, carSetDto);
    }
}
