package com.sudhirt.samples.country.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "Object not found")
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4853811968080348652L;

    public NotFoundException(String id) {
        super("Object with id " + id + " not found");
    }
}
