package com.peiload.ridecare.unitTest.car.service;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.car.repository.CarRepository;
import com.peiload.ridecare.car.service.CarService;
import com.peiload.ridecare.common.JwtTokenUtil;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private JwtTokenUtil jwtTokenUtilMock;
    @Mock
    private CarRepository carRepositoryMock;
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private CarService testObj;

    @BeforeEach
    public void setup(){
        testObj = new CarService(carRepositoryMock, jwtTokenUtilMock, userServiceMock);
    }

    @Test
    void findById_happyPath() {
        Car car1 = TestCar.getCar1();
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.of(car1));

        Car result = testObj.findById(car1.getId());

        assertNotNull(result);
        assertEquals(car1.getId(), result.getId());
        assertEquals(car1.getLicensePlate(), result.getLicensePlate());
        assertEquals(car1.getUser(), result.getUser());
        assertEquals(car1.getImage(), result.getImage());
        assertEquals(car1.getBrand(), result.getBrand());
        assertEquals(car1.getModel(), result.getModel());
        assertEquals(car1.getYear(), result.getYear());
        assertEquals(car1.getNumberOfDoors(), result.getNumberOfDoors());
        assertEquals(car1.getNumberOfSeats(), result.getNumberOfSeats());
        assertEquals(car1.getTransmission(), result.getTransmission());
        assertEquals(car1.getFuel(), result.getFuel());
    }

    @Test
    void findByIdWhenCarDoesntExist_shouldRaiseAnException() {
        when(carRepositoryMock.findById(any())).thenReturn((Optional.empty()));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.findById(0));

        assertEquals(HttpStatus.BAD_REQUEST.toString() + " \"Car does not exist.\"", exception.getMessage());
    }

    @Test
    void getUserCars_happyPath() {
        User user = TestUser.getUser1();
        List<Car> carList = TestCar.getUser1CarList();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user.getEmail());
        when(userServiceMock.findByEmail(user.getEmail())).thenReturn(user);
        when(carRepositoryMock.findAllByUser(user)).thenReturn(carList);


        List<CarShowDto> result = testObj.getUserCars("Bearer Token");

        assertNotNull(result);
        assertEquals(carList.size(), result.size());
        assertEquals(carList.get(0).getId(), result.get(0).getId());
    }

    @Test
    void createCar_happyPath() {
        User user1 = TestUser.getUser1();
        CarCreateDto car1 = TestCar.getCarCreateDto1();
        when(carRepositoryMock.findByLicensePlate(car1.getLicensePlate())).thenReturn(Optional.empty());
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user1.getEmail());
        when(userServiceMock.findByEmail(user1.getEmail())).thenReturn(user1);

        CarShowDto result = testObj.createCar("Bearer Token", car1);

        //Assert result
        assertNotNull(result);
        assertEquals(car1.getLicensePlate(), result.getLicensePlate());

        //Assert the saved object
        verify(carRepositoryMock, times(1)).save(
                argThat(newCar -> {
                    assertThat(newCar).isNotNull();
                    assertThat(newCar.getLicensePlate()).isEqualTo(car1.getLicensePlate());
                    assertThat(newCar.getUser().getId()).isEqualTo(user1.getId());
                    return true;
                })
        );
    }

    @Test
    void createCarWhenCarAlreadyExists_shouldRaiseAnException() {
        CarCreateDto carCreateDto = TestCar.getCarCreateDto1();
        Car car1 = TestCar.getCar1();
        when(carRepositoryMock.findByLicensePlate(carCreateDto.getLicensePlate())).thenReturn(Optional.of(car1));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.createCar("Bearer Token", carCreateDto));

        assertEquals(HttpStatus.BAD_REQUEST.toString() +  " \"There's already a car with this license plate.\"", exception.getMessage());
    }

    @Test
    void deleteCar_happyPath() {
        User user1 = TestUser.getUser1();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user1.getEmail());
        when(userServiceMock.findByEmail(user1.getEmail())).thenReturn(user1);
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.of(car1));

        testObj.deleteCar("Bearer Token", car1.getId());

        verify(carRepositoryMock, times(1)).delete(
                argThat(deletedCar -> {
                    assertThat(deletedCar).isNotNull();
                    assertThat(deletedCar.getLicensePlate()).isEqualTo(car1.getLicensePlate());
                    assertThat(deletedCar.getUser().getId()).isEqualTo(user1.getId());
                    return true;
                })
        );
    }

    @Test
    void deleteCarWhenCarDoesntBelongToUser_shouldRaiseAnException() {
        User user2 = TestUser.getUser2();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user2.getEmail());
        when(userServiceMock.findByEmail(user2.getEmail())).thenReturn(user2);
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.of(car1));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.deleteCar("Bearer Token", car1.getId()));

        assertEquals(HttpStatus.FORBIDDEN.toString() +  " \"The car you were trying to delete belongs to another user.\"", exception.getMessage());
    }

    @Test
    void deleteCarWhenCarDoesntExist_shouldRaiseAnException() {
        User user1 = TestUser.getUser1();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user1.getEmail());
        when(userServiceMock.findByEmail(user1.getEmail())).thenReturn(user1);
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.deleteCar("Bearer Token", car1.getId()));

        assertEquals(HttpStatus.BAD_REQUEST.toString() +  " \"Car does not exist.\"", exception.getMessage());
    }

    @Test
    void editCar_happyPath() {
        User user1 = TestUser.getUser1();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user1.getEmail());
        when(userServiceMock.findByEmail(user1.getEmail())).thenReturn(user1);
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.of(car1));

        CarEditDto carEditDto1 = TestCar.getCarEditDto1();

        testObj.editCar("Bearer Token", car1.getId(), carEditDto1);

        verify(carRepositoryMock, times(1)).save(
                argThat(editedCar -> {
                    assertThat(editedCar).isNotNull();
                    assertThat(editedCar.getLicensePlate()).isEqualTo(car1.getLicensePlate());
                    assertThat(editedCar.getUser().getId()).isEqualTo(user1.getId());
                    assertThat(editedCar.getYear()).isEqualTo(carEditDto1.getYear());
                    return true;
                })
        );
    }

    @Test
    void editCarWhenCarDoesntBelongToUser_shouldRaiseAnException() {
        User user2 = TestUser.getUser2();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user2.getEmail());
        when(userServiceMock.findByEmail(user2.getEmail())).thenReturn(user2);
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.of(car1));

        CarEditDto carEditDto1 = TestCar.getCarEditDto1();

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.editCar("Bearer Token", car1.getId(), carEditDto1));

        assertEquals(HttpStatus.FORBIDDEN.toString() +  " \"The car you were trying to edit belongs to another user.\"", exception.getMessage());
    }

    @Test
    void editCarWhenCarDoesntExist_shouldRaiseAnException() {
        User user1 = TestUser.getUser1();
        Car car1 = TestCar.getCar1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer Token")).thenReturn(user1.getEmail());
        when(carRepositoryMock.findById(car1.getId())).thenReturn(Optional.empty());

        CarEditDto carEditDto1 = TestCar.getCarEditDto1();

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.editCar("Bearer Token", car1.getId(), carEditDto1));

        assertEquals(HttpStatus.BAD_REQUEST.toString() +  " \"Car does not exist.\"", exception.getMessage());
    }

}
