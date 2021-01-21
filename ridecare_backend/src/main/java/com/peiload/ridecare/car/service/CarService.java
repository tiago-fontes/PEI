package com.peiload.ridecare.car.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.dto.StatusShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Value("${ridecare.datalake.url}")
    public String datalakeURL;

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
            //String status = getCurrentStatus(carId);
            //TODO car.setStatus(status);
            return new CarShowDto(car);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The car belongs to another user.");
        }
    }

    /*public List<CarShowDto> getOnlineCars(String authorizationToken) {
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
    }*/

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

    /*public String getCurrentStatus(int carId){
        Car car = findById(carId);
        String licensePlate = car.getLicensePlate();

        String url = datalakeURL + "/raspberry/status/"+ licensePlate;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        ObjectMapper objectMapper = new ObjectMapper();

        StatusShowDto status;
        try {
            status = objectMapper.readValue(rateResponse.getBody(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            status = new StatusShowDto("offline");
            e.printStackTrace();
        }

        return status.getStatus();
    }*/

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