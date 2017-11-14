package com.sudhirt.samples.country.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT, reason = "Country already exists")
public class CountryExistsException extends RuntimeException {

    private static final long serialVersionUID = 1464756881589264775L;

    public CountryExistsException(String iso3) {
        super("Country with Iso3 " + iso3 + " already exists");
    }
}
