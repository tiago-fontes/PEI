package com.peiload.ridecare.car.service;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
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
import java.util.stream.Collectors;


@Service
public class CarService {

    private final CarRepository carRepository;
    private final JwtTokenUtil jtu;
    private final UserService userService;

    public CarService(CarRepository carRepository, JwtTokenUtil jtu, UserService userService){
        this.carRepository = carRepository;
        this.jtu = jtu;
        this.userService = userService;
    }

    public Car findById(int carId) {
        return this.carRepository.findById(carId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car does not exist"));
    }

    public List<CarShowDto> getAllCars() {
        return this.carRepository.findAll().stream().map(CarShowDto::new).collect(Collectors.toList());
    }

    public List<CarShowDto> getUserCars(String authorizationToken) {
        List<CarShowDto> userCars = new ArrayList<>();
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        this.carRepository.findAllByUser(user).forEach(car -> userCars.add(new CarShowDto(car)));
        return userCars;
    }

    public CarShowDto createCar(String authorizationToken, CarCreateDto carCreateDto){
        Optional<Car> existingCar = this.carRepository.findByLicensePlate(carCreateDto.getLicensePlate());
        if(!(existingCar.isPresent())){
            String email = jtu.getEmailFromAuthorizationString(authorizationToken);
            User user = this.userService.findByEmail(email);
            Car car = new Car(carCreateDto, user);
            this.carRepository.save(car);
            return new CarShowDto(car);
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

    public void editCar(String authorizationToken, int id, CarEditDto carEditDto){
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        Optional<Car> existingCar = this.carRepository.findById(id);

        if(existingCar.isPresent() && existingCar.get().getUser().getEmail().equals(email)){
            Car car = existingCar.get();
            updateCar(car, carEditDto);
            this.carRepository.save(car);
        }
        else if(existingCar.isPresent() && !(existingCar.get().getUser().getEmail().equals(email))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to edit belongs to another user");
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's no car with this id");
        }
    }

    private void updateCar(Car car, CarEditDto carEditDto){
        if(carEditDto.getImage() != null){
            car.setImage(carEditDto.getImage());
        }
        if(carEditDto.getBrand() != null){
            car.setBrand(carEditDto.getBrand());
        }
        if(carEditDto.getModel() != null){
            car.setModel(carEditDto.getModel());
        }
        if(carEditDto.getYear() != null){
            car.setYear(carEditDto.getYear());
        }
        if(carEditDto.getNumberOfDoors() != null){
            car.setNumberOfDoors(carEditDto.getNumberOfDoors());
        }
        if(carEditDto.getNumberOfSeats() != null){
            car.setNumberOfSeats(carEditDto.getNumberOfDoors());
        }
        if(carEditDto.getTransmission() != null){
            car.setTransmission(carEditDto.getTransmission());
        }
        if(carEditDto.getFuel() != null){
            car.setFuel(carEditDto.getFuel());
        }
    }

}