package com.peiload.ridecare.unitTest.anomaly.service;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.anomaly.repository.AnomalyRepository;
import com.peiload.ridecare.anomaly.repository.MeasurementRepository;
import com.peiload.ridecare.anomaly.service.AnomalyService;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.car.service.CarService;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.unitTest.anomaly.model.TestAnomaly;
import com.peiload.ridecare.unitTest.car.model.TestCar;
import com.peiload.ridecare.unitTest.user.model.TestUser;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AnomalyServiceTest {

    @Mock
    private JwtTokenUtil jwtTokenUtilMock;
    @Mock
    private AnomalyRepository anomalyRepositoryMock;
    @Mock
    MeasurementRepository measurementRepositoryMock;
    @Mock
    private CarService carServiceMock;
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private AnomalyService testObj;

    @BeforeEach
    public void setup() {
        testObj = new AnomalyService(anomalyRepositoryMock, measurementRepositoryMock, jwtTokenUtilMock, userServiceMock, carServiceMock);
    }

    @Test
    void getAllAnomalies_happyPath() {
        List<Anomaly> anomalyList = TestAnomaly.getAnomalyList();
        when(anomalyRepositoryMock.findAll()).thenReturn(anomalyList);

        List<AnomalyShowDto> result = testObj.getAllAnomalies();

        assertNotNull(result);
        assertEquals(anomalyList.size(), result.size());
        assertEquals(anomalyList.get(0).getId(), result.get(0).getId());
        assertEquals(anomalyList.get(1).getId(), result.get(1).getId());
    }

    @Test
    void getLatestAnomaliesUser_happyPath(){
        User user = TestUser.getUser1();
        List<AnomalyShowDto> anomalyList = TestAnomaly.getUser1AnomalyList();

        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user.getEmail());
        when(userServiceMock.findByEmail(user.getEmail())).thenReturn(user);
        when(anomalyRepositoryMock.findAllByViewedAndCarIn(false, user.getCars())).thenReturn(anomalyList);


        List<AnomalyShowDto> result = testObj.getLatestAnomaliesUser("Bearer Token");

        assertNotNull(result);
        assertEquals(anomalyList.size(), result.size());
        assertEquals(anomalyList.get(0).getId(), result.get(0).getId());
    }

    @Test
    void getLatestAnomaliesCar_happyPath(){
        User user = TestUser.getUser1();
        Car car = TestCar.getCar1();
        List<AnomalyShowDto> anomalyList = TestAnomaly.getCar1AnomalyList();

        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user.getEmail());
        when(carServiceMock.findById(car.getId())).thenReturn(car);
        when(anomalyRepositoryMock.findAllByViewedAndCar(false, car)).thenReturn(anomalyList);

        List<AnomalyShowDto> result = testObj.getLatestAnomaliesCar("Bearer Token", car.getId());

        assertNotNull(result);
        assertEquals(anomalyList.size(), result.size());
        assertEquals(anomalyList.get(0).getId(), result.get(0).getId());
    }

    @Test
    void getLatestAnomaliesCarWhenNotOwnerOfCar_shouldRaiseAnException(){
        User user2 = TestUser.getUser2();
        Car car = TestCar.getCar1();

        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user2.getEmail());
        when(carServiceMock.findById(car.getId())).thenReturn(car);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.getLatestAnomaliesCar("Bearer Token", car.getId()));
        assertEquals(HttpStatus.FORBIDDEN.toString() +  " \"The car you were trying to view belongs to another user\"", exception.getMessage());

    }

}