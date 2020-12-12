package com.peiload.ridecare.integrationTest.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.login.model.JwtRequest;
import com.peiload.ridecare.user.dto.UserSetDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Random;


public class ITUtils {

    private static final Random RANDOM = new Random();

    private static final int leftLimit = 97; // letter 'a'
    private static final int rightLimit = 122; // letter 'z'

    public static String randomString(int length){
        return RANDOM.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static int randomInt(int min, int max){
        return new Random().nextInt(max - min) + min;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void createUser(String companyName, String email, String password){
        UserSetDto userSetDto = UserSetDto.builder()
                .companyName(companyName)
                .email(email)
                .password(password)
                .build();

        String body = ITUtils.asJsonString(userSetDto);

        RestAssured.given()
                .contentType(ContentType.JSON).body(body)
                .post("/register")
                .then().statusCode(201);
    }

    public static String loginUser(String email, String password){
        JwtRequest jwtRequest = new JwtRequest(email, password);
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post("/login")
                .then().statusCode(200).extract().response();

        return response.body().jsonPath().getString("token");
    }

    public static CarShowDto createCar(String license_plate, String userToken){
        CarCreateDto carCreateDto = CarCreateDto.builder()
                .licensePlate(license_plate)
                .image(randomString(10))
                .brand(randomString(10))
                .model(randomString(10))
                .year(randomInt(1970, 2021))
                .numberOfDoors(randomInt(2, 6))
                .numberOfSeats(randomInt(2, 6))
                .transmission(randomString(10))
                .fuel(randomString(10))
                .build();

        String body = asJsonString(carCreateDto);

        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON).body(body)
                .post("/car")
                .then().extract().response();

        return response.then().statusCode(201).extract().as(CarShowDto.class);
    }

    public static void deleteCar(String userToken, int carId){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .pathParam("carId", carId)
                .delete("/car" + "/{carId}")
                .then().statusCode(204);
    }

    public static void deleteUser(String userToken){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete("/user")
                .then().statusCode(204);
    }

}
