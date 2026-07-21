package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) {
        LOGGER.info("Inside main");

        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);

        try {
            testGetAllCountries();
            testFindCountryByCode();
            testAddCountry();
            testUpdateCountry();
            testFindCountriesByPartialName();
            testDeleteCountry();
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found", e);
        }
    }

    // Hands on 5: get all countries
    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries.size()={}", countries.size());
        LOGGER.info("End");
    }

    // Hands on 6: find a country based on country code
    private static void testFindCountryByCode() throws CountryNotFoundException {
        LOGGER.info("Start");
        Country country = countryService.findCountryByCode("IN");
        LOGGER.debug("Country:{}", country);
        LOGGER.info("End");
    }

    // Hands on 7: add a new country
    private static void testAddCountry() throws CountryNotFoundException {
        LOGGER.info("Start");
        Country newCountry = new Country("ZZ", "Testland");
        countryService.addCountry(newCountry);

        Country found = countryService.findCountryByCode("ZZ");
        LOGGER.debug("Added country found as:{}", found);
        LOGGER.info("End");
    }

    // Hands on 8: update a country based on code
    private static void testUpdateCountry() throws CountryNotFoundException {
        LOGGER.info("Start");
        countryService.updateCountry("ZZ", "Testland Updated");

        Country updated = countryService.findCountryByCode("ZZ");
        LOGGER.debug("Updated country:{}", updated);
        LOGGER.info("End");
    }

    // Hands on 5: find list of countries matching a partial country name
    private static void testFindCountriesByPartialName() {
        LOGGER.info("Start");
        List<Country> matches = countryService.findCountriesByPartialName("stan");
        LOGGER.debug("Countries matching 'stan':{}", matches);
        LOGGER.info("End");
    }

    // Hands on 9: delete a country based on code
    private static void testDeleteCountry() {
        LOGGER.info("Start");
        countryService.deleteCountry("ZZ");
        LOGGER.info("End");
    }
}
