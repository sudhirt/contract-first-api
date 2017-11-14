package com.sudhirt.samples.country.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST, reason = "Invalid input")
public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = -5388939737701312500L;

    public InvalidInputException(String msg) {
        super(msg);
    }
}
