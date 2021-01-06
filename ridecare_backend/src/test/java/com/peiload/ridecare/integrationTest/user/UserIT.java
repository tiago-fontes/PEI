package com.peiload.ridecare.integrationTest.user;

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
class UserIT {

    @LocalServerPort
    private int port;

    private static final String EMAIL = ITUtils.randomString(10) + "@email.com";
    private static final String COMPANY_NAME = ITUtils.randomString(10);
    private static final String PASSWORD = ITUtils.randomString(10);
    private static final String NEW_PASSWORD = ITUtils.randomString(10);
    private static String userToken;

    private static UserSetDto userSetDto;

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
    void createUserAgain_shouldRaiseAnException(){
        String body = ITUtils.asJsonString(userSetDto);

        RestAssured.given()
                .contentType(ContentType.JSON).body(body)
                .post(registerPath)
                .then().statusCode(403);
    }

    @Test
    @Order(3)
    void loginUser_withWrongPassword_shouldRaiseAnException(){
        JwtRequest jwtRequest = new JwtRequest(EMAIL, ITUtils.randomString(5));
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post(loginPath)
                .then().statusCode(403);
    }

    @Test
    @Order(4)
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
    @Order(5)
    void editUser(){
        UserSetDto userSetDto = UserSetDto.builder().password(NEW_PASSWORD).build();
        String body = ITUtils.asJsonString(userSetDto);

        RestAssured.given()
                .header("Authorization", "Bearer "+ userToken)
                .contentType(ContentType.JSON)
                .body(body)
                .patch(userPath)
                .then().statusCode(200);
    }

    @Test
    @Order(6)
    void loginUserWithOldPassword_shouldRaiseAnException(){
        JwtRequest jwtRequest = new JwtRequest(EMAIL, PASSWORD);
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post(loginPath)
                .then().statusCode(403);
    }

    @Test
    @Order(7)
    void loginUserAgainWithNewPassword_shouldWorkAndLoginSuccessfully(){
        JwtRequest jwtRequest = new JwtRequest(EMAIL, NEW_PASSWORD);
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post(loginPath)
                .then().statusCode(200).extract().response();

        userToken = response.body().jsonPath().getString("token");
    }


    @Test
    @Order(8)
    void deleteUser(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete(userPath)
                .then().statusCode(204);
    }


    @Test
    @Order(9)
    void loginUserAfterDeletingIt_shouldRaiseAnException(){
        JwtRequest jwtRequest = new JwtRequest(EMAIL, NEW_PASSWORD);
        String bodyCredentials = ITUtils.asJsonString(jwtRequest);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyCredentials)
                .post(loginPath)
                .then().statusCode(403);
    }


    @Test
    @Order(10)
    void deleteUserAgain_shouldRaiseAnException(){
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete(userPath)
                .then().statusCode(403);
    }


}
