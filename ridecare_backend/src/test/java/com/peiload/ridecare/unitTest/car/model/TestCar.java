package com.peiload.ridecare.unitTest.car.model;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.unitTest.user.model.TestUser;

import java.util.Arrays;
import java.util.List;

public class TestCar {

    public static Car getCar1(){
        return Car.builder()
                .id(1)
                .licensePlate("AA-11-AA")
                .user(TestUser.getUser1())
                .image("imagem1")
                .brand("BMW")
                .model("M4")
                .year(2017)
                .numberOfDoors(5)
                .numberOfSeats(5)
                .transmission("Manual")
                .fuel("Diesel")
                .build();
    }

    public static Car getCar2(){
        return Car.builder()
                .id(2)
                .licensePlate("BB-22-BB")
                .user(TestUser.getUser2())
                .image("imagem2")
                .brand("Renault")
                .model("Megane")
                .year(2015)
                .numberOfDoors(5)
                .numberOfSeats(5)
                .transmission("Manual")
                .fuel("Diesel")
                .build();
    }

    public static List<Car> getCarList() {
        return Arrays.asList(getCar1(), getCar2());
    }

    public static List<Car> getUser1CarList() {
        return Arrays.asList(getCar1());
    }

    public static CarCreateDto getCarCreateDto1() {
        return CarCreateDto.builder()
                .sensorId(1)
                .licensePlate("AA-11-AA")
                .image("imagem1")
                .brand("BMW")
                .model("M4")
                .year(2017)
                .numberOfDoors(5)
                .numberOfSeats(5)
                .transmission("Manual")
                .fuel("Diesel")
                .build();
    }

    public static CarEditDto getCarEditDto1() {
        return CarEditDto.builder()
                .year(2019)
                .build();
    }


}
