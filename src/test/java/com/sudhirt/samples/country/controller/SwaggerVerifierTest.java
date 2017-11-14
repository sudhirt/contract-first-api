package com.sudhirt.samples.country.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.atlassian.oai.validator.mockmvc.SwaggerValidatorMatchers.swagger;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/before.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/after.sql")
public class SwaggerVerifierTest {

    private final static String SWAGGER_JSON_URL = "static/swagger.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCountryById() throws Exception {
        mockMvc.perform(get("/countries/IND"))
                .andExpect(swagger().isValid(SWAGGER_JSON_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void getCountryByInvalidId() throws Exception {
        mockMvc.perform(get("/countries/XXX"))
                .andExpect(swagger().isValid(SWAGGER_JSON_URL))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getCountries() throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(swagger().isValid(SWAGGER_JSON_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(247)));
    }

    @Test
    public void getCountriesByIso2() throws Exception {
        mockMvc.perform(get("/countries?iso2=IN"))
                .andExpect(swagger().isValid(SWAGGER_JSON_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void getCountriesByInvalidIso2() throws Exception {
        mockMvc.perform(get("/countries?iso2=XX"))
                .andExpect(swagger().isValid(SWAGGER_JSON_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(0)));
    }
}
