package com.sudhirt.samples.country.service;

import com.sudhirt.samples.country.domain.Country;
import com.sudhirt.samples.country.exception.CountryExistsException;
import com.sudhirt.samples.country.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Country> getById(String iso3) {
        return countryRepository.findById(iso3.toUpperCase());
    }

    @Transactional(readOnly = true)
    public Optional<Country> getByIso2(String iso2) {
        return countryRepository.getByIso2(iso2.toUpperCase());
    }

    @Transactional(readOnly = true)
    public Optional<Country> getByNumericCode(String numericCode) {
        return countryRepository.getByNumericCode(numericCode);
    }

    @Transactional(readOnly = true)
    public List<Country> search(String queryString) {
        return countryRepository.search("%" + queryString.toUpperCase() + "%");
    }

    public Country save(@Valid Country country) {
        if (getById(country.getIso3()).isPresent()) {
            throw new CountryExistsException(country.getIso3());
        } else {
            return countryRepository.save(country);
        }
    }
}
