package com.peiload.ridecare.integrationTest.car;

import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.integrationTest.common.ITUtils;
import com.peiload.ridecare.login.model.JwtRequest;
import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.dto.UserShowDto;
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
    private static final int YEAR = ITUtils.randomYear(1970, 2021);
    private static final int NUMBER_OF_DOORS = ITUtils.randomInt(2, 6);
    private static final int NUMBER_OF_SEATS = ITUtils.randomInt(2, 6);
    private static final String TRANSMISSION = ITUtils.randomString(10);
    private static final String FUEL = ITUtils.randomString(10);

    private static final String EMAIL = ITUtils.randomString(10) + "@email.com";
    private static final String COMPANY_NAME = ITUtils.randomString(10);
    private static final String PASSWORD = ITUtils.randomString(10);


    private static CarCreateDto carCreateDto;
    private static UserSetDto userSetDto;


    private static String userToken;


    private static final String carPath = "/car";

    private static final String userPath = "/user";
    private static final String loginPath = "/login";
    private static final String registerPath = "/register";

    @BeforeEach
    public void setUp(){
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void createUser(){
        userSetDto = UserSetDto.builder()
                .companyName(COMPANY_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        String body = ITUtils.asJsonString(userSetDto);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON).body(body)
                .post(registerPath)
                .then().extract().response();

        UserShowDto user = response.then().statusCode(201).extract().as(UserShowDto.class);
        assertEquals(COMPANY_NAME, user.getCompanyName());
        assertEquals(EMAIL, user.getEmail());
    }


    @Test
    @Order(2)
    void loginUser(){
        JwtRequest jwtRequest = new JwtRequest(EMAIL, PASSWORD);
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post(loginPath)
                .then().statusCode(200).extract().response();

        userToken = response.body().jsonPath().getString("token");
    }

    @Test
    @Order(3)
    void createCar(){
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

        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON).body(body)
                .post(carPath)
                .then().extract().response();

        CarShowDto car = response.then().statusCode(201).extract().as(CarShowDto.class);

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
    void deleteCar(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete(carPath + "/" + carCreateDto.getLicensePlate())
                .then().statusCode(204);
    }

    @Test
    @Order(5)
    void deleteUser(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete(userPath)
                .then().statusCode(204);
    }
}
