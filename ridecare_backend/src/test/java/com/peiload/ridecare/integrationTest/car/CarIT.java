package com.peiload.ridecare.integrationTest.car;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarEditDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.integrationTest.common.ITUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-integration.test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarIT {

    @LocalServerPort
    private int port;

    private static final String LICENSE_PLATE = ITUtils.randomString(2) + "-" + ITUtils.randomString(2) + "-" + ITUtils.randomString(2);
    private static final String IMAGE = ITUtils.randomString(10);
    private static final String BRAND = ITUtils.randomString(10);
    private static final String MODEL = ITUtils.randomString(10);
    private static final int YEAR = ITUtils.randomInt(1970, 2021);
    private static final int NUMBER_OF_DOORS = ITUtils.randomInt(2, 6);
    private static final int NUMBER_OF_SEATS = ITUtils.randomInt(2, 6);
    private static final String TRANSMISSION = ITUtils.randomString(10);
    private static final String FUEL = ITUtils.randomString(10);
    private static final String NEW_BRAND = ITUtils.randomString(10);

    private static final String EMAIL = ITUtils.randomString(10) + "@email.com";
    private static final String COMPANY_NAME = ITUtils.randomString(10);
    private static final String PASSWORD = ITUtils.randomString(10);


    private static String userToken;

    private static String userToken2;
    private static String licensePlate2;
    private static int carId2;


    private static CarCreateDto carCreateDto;

    private static int carId;

    private static final String carPath = "/car";

    @BeforeEach
    public void setUp(){
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void createLoginUser(){
        ITUtils.createUser(COMPANY_NAME, EMAIL, PASSWORD);
        userToken = ITUtils.loginUser(EMAIL, PASSWORD);
    }

    @Test
    @Order(2)
    void createCarWithoutAuthorizationKey_shouldRaiseAnException(){
        carCreateDto = CarCreateDto.builder()
                .licensePlate(LICENSE_PLATE)
                .image(IMAGE)
                .brand(BRAND)
                .model(MODEL)
                .year(YEAR)
                .numberOfDoors(NUMBER_OF_DOORS)
                .numberOfSeats(NUMBER_OF_SEATS)
                .transmission(TRANSMISSION)
                .fuel(FUEL)
                .build();

        String body = ITUtils.asJsonString(carCreateDto);

        RestAssured.given()
                .contentType(ContentType.JSON).body(body)
                .post(carPath)
                .then().statusCode(403);
    }

    @Test
    @Order(3)
    void createCar_happyPath(){
        String body = ITUtils.asJsonString(carCreateDto);

        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON).body(body)
                .post(carPath)
                .then().extract().response();

        CarShowDto car = response.then().statusCode(201).extract().as(CarShowDto.class);

        carId = car.getId();

        assertEquals(LICENSE_PLATE, car.getLicensePlate());
        assertEquals(IMAGE, car.getImage());
        assertEquals(BRAND, car.getBrand());
        assertEquals(MODEL, car.getModel());
        assertEquals(YEAR, car.getYear());
        assertEquals(NUMBER_OF_DOORS, car.getNumberOfDoors());
        assertEquals(NUMBER_OF_SEATS, car.getNumberOfSeats());
        assertEquals(TRANSMISSION, car.getTransmission());
        assertEquals(FUEL, car.getFuel());
    }

    @Test
    @Order(4)
    void createCarAgain_shouldRaiseAnException(){
        String body = ITUtils.asJsonString(carCreateDto);

        RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON).body(body)
                .post(carPath)
                .then().statusCode(403);
    }

    @Test
    @Order(5)
    void getUserCars_happyPath(){
        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .get(carPath)
                .then().extract().response();

        List<CarShowDto> companyCars = response.then().statusCode(200).extract().body().jsonPath().getList(".", CarShowDto.class);

        assertEquals(1, companyCars.size());
    }

    @Test
    @Order(6)
    void editCar_happyPath(){
        CarEditDto carEditDto = CarEditDto.builder()
                .brand(NEW_BRAND)
                .build();

        String body = ITUtils.asJsonString(carEditDto);

        RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("id", carId)
                .patch(carPath + "/{id}")
                .then().statusCode(200);
    }

    @Test
    @Order(7)
    void createLoginUser2AndCreateCar2(){
        String company = ITUtils.randomString(10);
        String email = ITUtils.randomString(10) + "@email.com";
        String password = ITUtils.randomString(10);

        ITUtils.createUser(company, email, password);

        userToken2 = ITUtils.loginUser(email, password);
        licensePlate2 = ITUtils.randomString(2) + "-" + ITUtils.randomString(2) + "-" + ITUtils.randomString(2);

        CarShowDto car = ITUtils.createCar(licensePlate2, userToken2);
        carId2 = car.getId();
    }

    //verificar
    @Test
    @Order(8)
    void editCarFromOtherUser_shouldRaiseAnException(){
        CarEditDto carEditDto = CarEditDto.builder()
                .brand(NEW_BRAND)
                .build();

        String body = ITUtils.asJsonString(carEditDto);

        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("id", carId2)
                .patch(carPath + "/{id}")
                .then().statusCode(403);
    }

    //verificar
    @Test
    @Order(9)
    void editCarThatDoesntExist_shouldRaiseAnException(){
        CarEditDto carEditDto = CarEditDto.builder()
                .brand(NEW_BRAND)
                .build();

        String body = ITUtils.asJsonString(carEditDto);

        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("id", 321312)
                .patch(carPath + "/{id}")
                .then().statusCode(403);
    }

    @Test
    @Order(10)
    void deleteCar_happyPath(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .pathParam("license_plate", carCreateDto.getLicensePlate())
                .delete(carPath + "/{license_plate}")
                .then().statusCode(204);
    }

    @Test
    @Order(11)
    void deleteCarAgain_shouldRaiseAnException(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .pathParam("license_plate", carCreateDto.getLicensePlate())
                .delete(carPath + "/{license_plate}")
                .then().statusCode(403);
    }

    //verificar
    @Test
    @Order(12)
    void deleteCarFromOtherUser_shouldRaiseAnException(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .pathParam("license_plate", licensePlate2)
                .delete(carPath + "/{license_plate}")
                .then().statusCode(403);
    }

    @Test
    @Order(13)
    void deleteCar2(){
        ITUtils.deleteCar(userToken2, licensePlate2);
    }

    @Test
    @Order(14)
    void deleteUsers(){
        ITUtils.deleteUser(userToken);
        ITUtils.deleteUser(userToken2);
    }
}
