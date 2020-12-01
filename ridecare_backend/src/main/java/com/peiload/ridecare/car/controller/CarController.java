package com.peiload.ridecare.car.controller;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.service.CarService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public void createCar(@RequestHeader("Authorization") String authorizationToken, @RequestBody CarCreateDto carCreateDto){
        this.carService.createCar(authorizationToken, carCreateDto);
    }

    @DeleteMapping(path="/{licensePlate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable String licensePlate){
        this.carService.deleteCar(authorizationToken, licensePlate);
    }

    @PatchMapping(path="/edit/{id}")
    public void editCar(@RequestHeader("Authorization") String authorizationToken, @PathVariable int id, @RequestBody CarEditDto carEditDto){
        this.carService.editCar(authorizationToken, id, carEditDto);
    }
}
