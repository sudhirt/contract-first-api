package com.sudhirt.samples.country.controller;

import com.sudhirt.samples.country.domain.Country;
import com.sudhirt.samples.country.exception.InvalidInputException;
import com.sudhirt.samples.country.exception.NotFoundException;
import com.sudhirt.samples.country.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    private final CountryService countryService;

    CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> get(@RequestParam String countryId, @RequestParam String iso2, @RequestParam String numericCode) {
        List<Country> countries = new ArrayList<>();
        if (countryId == null && iso2 == null && numericCode == null) {
            countries.addAll(countryService.getAll());
            return countries;
        }
        Optional<Country> country;
        if (countryId != null && iso2 == null && numericCode == null) {
            country = countryService.getById(countryId);
        } else if (countryId == null && iso2 != null && numericCode == null) {
            country = countryService.getByIso2(iso2);
        } else if (countryId == null && iso2 == null) {
            country = countryService.getByNumericCode(numericCode);
        } else {
            throw new InvalidInputException("Provide only one input: countryId or iso2 or numericCode");
        }
        country.ifPresent(countries::add);
        return countries;
    }

    @GetMapping("/{countryId}")
    public Country getById(@PathVariable String countryId) {
        Optional<Country> countryHolder = countryService.getById(countryId);
        if (countryHolder.isPresent()) {
            return countryHolder.get();
        } else {
            throw new NotFoundException(countryId);
        }
    }

    @GetMapping("/search")
    public List<Country> search(@RequestParam String q) {
        if (StringUtils.isEmpty(q)) {
            throw new InvalidInputException("Invalid search criteria");
        }
        return countryService.search(q);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Country country) {
        countryService.save(country);
        return status(CREATED).build();
    }
}
