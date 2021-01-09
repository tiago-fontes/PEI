package com.peiload.ridecare.user.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends RuntimeException{

    public UserException(String message){
        super(message);
    }

}
