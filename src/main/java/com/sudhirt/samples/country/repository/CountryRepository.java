package com.sudhirt.samples.country.repository;

import com.sudhirt.samples.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    Optional<Country> getByIso2(String iso2);

    Optional<Country> getByNumericCode(String numericCode);

    @Query("select c from Country c where c.iso2 like ?1 or c.iso3 like ?1 or c.numericCode like ?1 or UPPER(c.name) like ?1")
    List<Country> search(String queryString);
}
