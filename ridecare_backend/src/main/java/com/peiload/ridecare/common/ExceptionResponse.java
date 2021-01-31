package com.peiload.ridecare.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ExceptionResponse {

    private HttpStatus status;
    private Date date;
    private String message;
    private List<String> errors;

    public ExceptionResponse(final HttpStatus status, Date date, final String message, final List<String> errors) {
        super();
        this.status = status;
        this.date = date;
        this.message = message;
        this.errors = errors;
    }

    public ExceptionResponse(final HttpStatus status, Date date, final String message, final String error) {
        super();
        this.status = status;
        this.date = date;
        this.message = message;
        errors = Collections.singletonList(error);
    }

}
