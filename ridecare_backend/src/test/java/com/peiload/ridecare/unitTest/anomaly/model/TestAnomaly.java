package com.peiload.ridecare.unitTest.anomaly.model;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.unitTest.car.model.TestCar;
import com.peiload.ridecare.unitTest.user.model.TestUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestAnomaly {

    public static Anomaly getAnomaly1(){
        return Anomaly.builder()
                .id(1)
                .car(TestCar.getCar1())
                .viewed(false)
                .measurements(Collections.singletonList(TestMeasurement.getMeasurement1()))
                .build();
    }
    public static Anomaly getAnomaly2(){
        return Anomaly.builder()
                .id(2)
                .car(TestCar.getCar2())
                .viewed(false)
                .measurements(Collections.singletonList(TestMeasurement.getMeasurement4()))
                .build();
    }

    public static AnomalyShowDto getAnomaly1CreateDto1(){
        return AnomalyShowDto.builder()
                .id(1)
                .viewed(false)
                .measurements(Collections.singletonList(TestMeasurement.getMeasurement1CreateDto1()))
                .build();
    }

    public static List<Anomaly> getAnomalyList() {
        return Arrays.asList(getAnomaly1(), getAnomaly2());
    }

    public static List<AnomalyShowDto> getUser1AnomalyList() {
        return Arrays.asList(getAnomaly1CreateDto1());
    }

    public static List<AnomalyShowDto> getCar1AnomalyList() {return Arrays.asList(getAnomaly1CreateDto1());}
}
