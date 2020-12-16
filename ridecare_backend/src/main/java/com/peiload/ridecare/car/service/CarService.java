package com.peiload.ridecare.car.service;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.dto.StatusHistoryRequestDto;
import com.peiload.ridecare.car.dto.StatusHistoryShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.model.CarStatus;
import com.peiload.ridecare.car.model.StatusHistory;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public CarService(CarRepository carRepository, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.carRepository = carRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public Car findById(int carId) {
        return this.carRepository.findById(carId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car does not exist."));
    }

    public List<CarShowDto> getAllCars() {
        return this.carRepository.findAll().stream().map(CarShowDto::new).collect(Collectors.toList());
    }

    public CarShowDto getCarById(String authorizationToken, int carId) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        Car car = findById(carId);
        if (car.getUser().getId() == user.getId()) {
            return new CarShowDto(car);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car belongs to another user.");
        }
    }

    public List<CarShowDto> getOnlineCars(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        return this.carRepository.findAllByUserAndStatus(user, CarStatus.ONLINE).stream().map(CarShowDto::new).collect(Collectors.toList());
    }


    public List<CarShowDto> getOfflineCars(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        return this.carRepository.findAllByUserAndStatus(user, CarStatus.ONLINE).stream().map(CarShowDto::new).collect(Collectors.toList());
    }

    public List<CarShowDto> getUserCars(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        return this.carRepository.findAllByUser(user).stream().map(CarShowDto::new).collect(Collectors.toList());
    }

    public CarShowDto createCar(String authorizationToken, CarCreateDto carCreateDto) {
        Optional<Car> existingCar = this.carRepository.findByLicensePlate(carCreateDto.getLicensePlate());
        if (!(existingCar.isPresent())) {
            String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
            User user = this.userService.findByEmail(email);
            Car car = new Car(carCreateDto, user);
            this.carRepository.save(car);
            return new CarShowDto(car);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's already a car with this license plate.");
        }
    }

    public void editCar(String authorizationToken, int carId, CarEditDto carEditDto) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = userService.findByEmail(email);
        Car car = findById(carId);

        if (car.getUser().getId() == user.getId()) {
            updateCar(car, carEditDto);
            this.carRepository.save(car);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to edit belongs to another user.");
        }
    }

    public void deleteCar(String authorizationToken, int carId) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = userService.findByEmail(email);
        Car car = findById(carId);

        if (car.getUser().getId() == user.getId()) {
            this.carRepository.delete(car);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car you were trying to delete belongs to another user.");
        }
    }

    private void updateCar(Car car, CarEditDto carEditDto) {
        if (carEditDto.getLicencePlate() != null) {
            car.setLicensePlate(carEditDto.getLicencePlate());
        }
        if (carEditDto.getImage() != null) {
            car.setImage(carEditDto.getImage());
        }
        if (carEditDto.getBrand() != null) {
            car.setBrand(carEditDto.getBrand());
        }
        if (carEditDto.getModel() != null) {
            car.setModel(carEditDto.getModel());
        }
        if (carEditDto.getYear() != null) {
            car.setYear(carEditDto.getYear());
        }
        if (carEditDto.getNumberOfDoors() != null) {
            car.setNumberOfDoors(carEditDto.getNumberOfDoors());
        }
        if (carEditDto.getNumberOfSeats() != null) {
            car.setNumberOfSeats(carEditDto.getNumberOfDoors());
        }
        if (carEditDto.getTransmission() != null) {
            car.setTransmission(carEditDto.getTransmission());
        }
        if (carEditDto.getFuel() != null) {
            car.setFuel(carEditDto.getFuel());
        }
    }

    public List<StatusHistoryShowDto> getStatusHistoryBetweenDates(int carId, Date initialDate, Date finalDate) {
        Car car = findById(carId);
        List<StatusHistoryShowDto> history = new ArrayList<>();
        for(int i = 0; i < car.getStatusHistory().size(); i++){
            StatusHistory sh = car.getStatusHistory().get(i);
            if(sh.getDate().after(initialDate)){
                if(sh.getDate().before(finalDate)){
                    history.add(new StatusHistoryShowDto(sh));
                }
                else{
                    return history;
                }
            }
        }

        return history;
    }
}