package com.peiload.ridecare.car.service;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
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
        List<Car> cars = this.carRepository.findAllByUser(user);
        return cars.stream().filter(car -> car.getStatusHistory().get(car.getStatusHistory().size()-1).getStatus().equals(CarStatus.ONLINE)).map(CarShowDto::new).collect(Collectors.toList());
    }

    public List<CarShowDto> getOfflineCars(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.findByEmail(email);
        List<Car> cars = this.carRepository.findAllByUser(user);
        return cars.stream().filter(car -> car.getStatusHistory().get(car.getStatusHistory().size()-1).getStatus().equals(CarStatus.OFFLINE)).map(CarShowDto::new).collect(Collectors.toList());
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
            StatusHistory sh = new StatusHistory(CarStatus.OFFLINE, new Date(), car);
            car.getStatusHistory().add(sh);
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

    public StatusHistoryShowDto getCurrentStatus(int carId){
        Car car = findById(carId);
        return new StatusHistoryShowDto(car.getStatusHistory().get(car.getStatusHistory().size()-1));
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

    public List<StatusHistoryShowDto> getLatestStatusHistory(int carId, int hours) {
        Car car = findById(carId);
        List<StatusHistoryShowDto> history = new ArrayList<>();

        Date initialDate = new Date(System.currentTimeMillis() - (hours * 60 * 60 * 1000));

        List<StatusHistory> shList = car.getStatusHistory();

        for(int i = car.getStatusHistory().size()-1; i >= 0 && shList.get(i).getDate().after(initialDate); i--){
            StatusHistory sh = shList.get(i);
            history.add(new StatusHistoryShowDto(sh));
        }

        return history;
    }

    public User findUserByCarId(int carId) {
        return findById(carId).getUser();
    }

    public Boolean verify(String licensePlate, int sensorId) {
        Optional<Car> car = this.carRepository.findByLicensePlate(licensePlate);
        if(car.isEmpty()){
            return false;
        }
        else{
            return (car.get().getSensorId() == sensorId);
        }
    }
}