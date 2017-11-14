package com.sudhirt.samples.country.service;

import com.sudhirt.samples.country.domain.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/after.sql")
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    public void searchWithCountryName() {
        List<Country> searchResults = countryService.search("India");
        searchResults.stream().forEach(result -> assertThat(result.getName()).containsIgnoringCase("India"));
    }

    @Test
    public void searchWithFullCountryName() {
        List<Country> searchResults = countryService.search("Indonesia");
        assertThat(searchResults.size()).isEqualTo(1);
        assertThat(searchResults.get(0).getIso3()).isEqualTo("IDN");
        assertThat(searchResults.get(0).getIso2()).isEqualTo("ID");
        assertThat(searchResults.get(0).getNumericCode()).isEqualTo("360");
        assertThat(searchResults.get(0).getName()).isEqualTo("Indonesia");
    }

    @Test
    public void searchWithIso3() {
        List<Country> searchResults = countryService.search("IND");
        assertThat(searchResults.size()).isEqualTo(3);
    }

    @Test
    public void searchWithIso2() {
        List<Country> searchResults = countryService.search("KP");
        assertThat(searchResults.size()).isEqualTo(1);
        assertThat(searchResults.get(0).getIso2()).isEqualTo("KP");
        assertThat(searchResults.get(0).getIso3()).isEqualTo("PRK");
        assertThat(searchResults.get(0).getNumericCode()).isEqualTo("408");
    }

    @Test
    public void searchWithNumericCode() {
        List<Country> searchResults = countryService.search("356");
        assertThat(searchResults.size()).isEqualTo(1);
    }

    @Test
    public void getByIso3() {
        Optional<Country> searchResult = countryService.getById("IND");
        assertThat(searchResult).isPresent();
        assertThat(searchResult.get().getIso2()).isEqualTo("IN");
        assertThat(searchResult.get().getIso3()).isEqualTo("IND");
        assertThat(searchResult.get().getNumericCode()).isEqualTo("356");
    }

    @Test
    public void getByInvalidIso3() {
        Optional<Country> searchResult = countryService.getById("XYZ");
        assertThat(searchResult).isNotPresent();
    }

    @Test
    public void getByIso2() {
        Optional<Country> searchResult = countryService.getByIso2("IN");
        assertThat(searchResult).isPresent();
        assertThat(searchResult.get().getIso2()).isEqualTo("IN");
        assertThat(searchResult.get().getIso3()).isEqualTo("IND");
        assertThat(searchResult.get().getNumericCode()).isEqualTo("356");
    }

    @Test
    public void getByNumericCode() {
        Optional<Country> searchResult = countryService.getByNumericCode("356");
        assertThat(searchResult).isPresent();
        assertThat(searchResult.get().getIso2()).isEqualTo("IN");
        assertThat(searchResult.get().getIso3()).isEqualTo("IND");
        assertThat(searchResult.get().getNumericCode()).isEqualTo("356");
    }
}
