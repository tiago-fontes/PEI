package com.peiload.ridecare.user.model;

import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.dto.UserShowDto;

import java.util.Arrays;
import java.util.List;

public class TestUser {

    public static User getUser1(){
        return User.builder()
                .id(1)
                .email("testUser1@email.com")
                .companyName("Company Test1")
                .password("test1")
                .build();
    }

    public static User getUser2(){
        return User.builder()
                .id(2)
                .email("testUser2@email.com")
                .companyName("Company Test2")
                .password("test2")
                .build();
    }

    public static UserSetDto getUserSetDto1() {
        return UserSetDto.builder()
                .email("testUser1@email.com")
                .companyName("Company Test1")
                .password("test1")
                .build();
    }

    public static UserSetDto getUserSetDto2() {
        return UserSetDto.builder()
                .email("testUser2@email.com")
                .companyName("Company Test2")
                .password("test2")
                .build();
    }

    public static UserShowDto getUserShowDto1(){
        return new UserShowDto(getUser1());
    }

    public static UserShowDto getUserShowDto2(){
        return new UserShowDto(getUser2());
    }

    public static List<User> getUsersList(){
        return Arrays.asList(getUser1(), getUser2());
    }

    public static List<UserShowDto> getUserShowDtoList(){
        return Arrays.asList(getUserShowDto1(), getUserShowDto2());
    }

}
