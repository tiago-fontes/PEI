package com.peiload.ridecare.car.service;

import com.peiload.ridecare.car.dto.CarSetDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {

    private final CarRepository carRepository;
    private final JwtTokenUtil jtu;
    private final UserService userService;



    public CarService(CarRepository carRepository, JwtTokenUtil jtu, UserService userService){//, CarMapper carMapper){
        this.carRepository = carRepository;
        this.jtu = jtu;
        this.userService = userService;
        //this.carMapper = carMapper;
    }

    public List<CarShowDto> getAllCars() {
        List<CarShowDto> allCars = new ArrayList<>();
        this.carRepository.findAll().forEach(car -> allCars.add(new CarShowDto(car)));
        return allCars;
    }

    public List<CarShowDto> getUserCars(String authorizationToken) {
        List<CarShowDto> userCars = new ArrayList<>();
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        this.carRepository.findAllByUser(user).forEach(car -> userCars.add(new CarShowDto(car)));
        return userCars;
    }

    public void createCar(String authorizationToken, CarSetDto carSetDto){
        Optional<Car> existingCar = this.carRepository.findByLicensePlate(carSetDto.getLicensePlate());
        if(!(existingCar.isPresent())){
            String email = jtu.getEmailFromAuthorizationString(authorizationToken);
            User user = this.userService.findByEmail(email);
            Car car = new Car(carSetDto, user);
            this.carRepository.save(car);
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's already a car with this license plate");
        }
    }

    public void deleteCar(String authorizationToken, String licensePlate){
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        Optional<Car> existingCar = this.carRepository.findByLicensePlate(licensePlate);
        if(existingCar.isPresent() && existingCar.get().getUser().getEmail().equals(email)){
            this.carRepository.delete(existingCar.get());
        }
        else if(existingCar.isPresent() && !(existingCar.get().getUser().getEmail().equals(email))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to delete belongs to another user");
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's no car with license plate: " + licensePlate);
        }
    }

    public void editCar(String authorizationToken, int id, CarSetDto carSetDto){
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        Optional<Car> existingCar = this.carRepository.findById(id);

        if(existingCar.isPresent() && existingCar.get().getUser().getEmail().equals(email)){
            Car car = existingCar.get();
            updateCar(car, carSetDto);
            this.carRepository.save(car);
        }
        else if(existingCar.isPresent() && !(existingCar.get().getUser().getEmail().equals(email))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to edit belongs to another user");
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's no car with this id");
        }
    }

    private void updateCar(Car car, CarSetDto carSetDto){
        if(carSetDto.getLicensePlate() != null){
            car.setLicensePlate(carSetDto.getLicensePlate());
        }
        if(carSetDto.getBrand() != null){
            car.setBrand(carSetDto.getBrand());
        }
        if(carSetDto.getModel() != null){
            car.setModel(carSetDto.getModel());
        }
        if(carSetDto.getYear() != null){
            car.setYear(carSetDto.getYear());
        }
        if(carSetDto.getNumberOfDoors() != null){
            car.setNumberOfDoors(carSetDto.getNumberOfDoors());
        }
        if(carSetDto.getNumberOfSeats() != null){
            car.setNumberOfSeats(carSetDto.getNumberOfDoors());
        }
        if(carSetDto.getTransmission() != null){
            car.setTransmission(carSetDto.getTransmission());
        }
        if(carSetDto.getFuel() != null){
            car.setFuel(carSetDto.getFuel());
        }
    }

    public Car findById(int carId) {
        return this.carRepository.findById(carId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}