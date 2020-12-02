package com.peiload.ridecare.integrationTest.common;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static int randomYear(int min, int max){
        return new Random().nextInt(max - min) + min;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
