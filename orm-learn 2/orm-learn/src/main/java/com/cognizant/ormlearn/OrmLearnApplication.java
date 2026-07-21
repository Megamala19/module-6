package com.cognizant.ormlearn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.repository.StockRepository;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;
import com.cognizant.ormlearn.service.StockService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;
    private static CountryRepository countryRepository;
    private static StockRepository stockRepository;
    private static StockService stockService;
    private static EmployeeService employeeService;
    private static DepartmentService departmentService;
    private static SkillService skillService;

    public static void main(String[] args) {
        LOGGER.info("Inside main");

        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);
        countryRepository = context.getBean(CountryRepository.class);
        stockRepository = context.getBean(StockRepository.class);
        stockService = context.getBean(StockService.class);
        employeeService = context.getBean(EmployeeService.class);
        departmentService = context.getBean(DepartmentService.class);
        skillService = context.getBean(SkillService.class);

        try {
            // --- Doc 1: Country CRUD ---
            testGetAllCountries();
            testFindCountryByCode();
            testAddCountry();
            testUpdateCountry();
            testFindCountriesByPartialName();
            testDeleteCountry();

            // --- Doc 2, Hands on 1: Country Query Methods ---
            testFindCountriesContaining();
            testFindCountriesContainingOrderByNameAsc();
            testFindCountriesStartingWith();

            // --- Doc 2, Hands on 2: Stock Query Methods ---
            testFindFacebookStockInSeptember2019();
            testFindGoogleStockOpenGreaterThan1250();
            testFindTop3FacebookDaysByVolume();
            testFindTop3NetflixLowestCloseDays();

            // --- Doc 2, Hands on 4: @ManyToOne (Employee -> Department) ---
            testGetEmployee();
            testAddEmployee();
            testUpdateEmployee();

            // --- Doc 2, Hands on 5: @OneToMany (Department -> Employees) ---
            testGetDepartment();

            // --- Doc 2, Hands on 6: @ManyToMany (Employee <-> Skill) ---
            testAddSkillToEmployee();
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found", e);
        }
    }

    // =====================================================================
    // Doc 1 - Country CRUD (Hands on 5-9)
    // =====================================================================

    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries.size()={}", countries.size());
        LOGGER.info("End");
    }

    private static void testFindCountryByCode() throws CountryNotFoundException {
        LOGGER.info("Start");
        Country country = countryService.findCountryByCode("IN");
        LOGGER.debug("Country:{}", country);
        LOGGER.info("End");
    }

    private static void testAddCountry() throws CountryNotFoundException {
        LOGGER.info("Start");
        Country newCountry = new Country("ZZ", "Testland");
        countryService.addCountry(newCountry);

        Country found = countryService.findCountryByCode("ZZ");
        LOGGER.debug("Added country found as:{}", found);
        LOGGER.info("End");
    }

    private static void testUpdateCountry() throws CountryNotFoundException {
        LOGGER.info("Start");
        countryService.updateCountry("ZZ", "Testland Updated");

        Country updated = countryService.findCountryByCode("ZZ");
        LOGGER.debug("Updated country:{}", updated);
        LOGGER.info("End");
    }

    private static void testFindCountriesByPartialName() {
        LOGGER.info("Start");
        List<Country> matches = countryService.findCountriesByPartialName("stan");
        LOGGER.debug("Countries matching 'stan':{}", matches);
        LOGGER.info("End");
    }

    private static void testDeleteCountry() {
        LOGGER.info("Start");
        countryService.deleteCountry("ZZ");
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2, Hands on 1 - Country Query Methods
    // =====================================================================

    // Search-as-you-type: countries whose name contains 'ou'
    private static void testFindCountriesContaining() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByNameContaining("ou");
        LOGGER.debug("Countries containing 'ou':{}", countries);
        LOGGER.info("End");
    }

    // Same search, but sorted ascending by name
    private static void testFindCountriesContainingOrderByNameAsc() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByNameContainingOrderByNameAsc("ou");
        LOGGER.debug("Countries containing 'ou', ascending:{}", countries);
        LOGGER.info("End");
    }

    // Alphabet index: countries starting with 'Z'
    private static void testFindCountriesStartingWith() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByNameStartingWith("Z");
        LOGGER.debug("Countries starting with 'Z':{}", countries);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2, Hands on 2 - Stock Query Methods
    // =====================================================================

    private static void testFindFacebookStockInSeptember2019() {
        LOGGER.info("Start");
        List<Stock> stocks = stockRepository.findByCodeAndDateBetween(
                "FB", LocalDate.of(2019, 9, 1), LocalDate.of(2019, 9, 30));
        LOGGER.debug("FB stock, Sep 2019:{}", stocks);
        LOGGER.info("End");
    }

    private static void testFindGoogleStockOpenGreaterThan1250() {
        LOGGER.info("Start");
        List<Stock> stocks = stockService.findByCodeAndOpenGreaterThan("GOOGL", new BigDecimal("1250"));
        LOGGER.debug("GOOGL stock, open > 1250:{}", stocks);
        LOGGER.info("End");
    }

    private static void testFindTop3FacebookDaysByVolume() {
        LOGGER.info("Start");
        List<Stock> stocks = stockService.findTop3ByCodeOrderByVolumeDesc("FB");
        LOGGER.debug("FB, top 3 highest-volume days:{}", stocks);
        LOGGER.info("End");
    }

    private static void testFindTop3NetflixLowestCloseDays() {
        LOGGER.info("Start");
        List<Stock> stocks = stockService.findTop3ByCodeOrderByCloseAsc("NFLX");
        LOGGER.debug("NFLX, top 3 lowest-close days:{}", stocks);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2, Hands on 4 - @ManyToOne (Employee -> Department)
    // =====================================================================

    private static void testGetEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(1);
        LOGGER.debug("Employee:{}", employee);
        LOGGER.debug("Department:{}", employee.getDepartment());
        LOGGER.debug("Skills:{}", employee.getSkillList());
        LOGGER.info("End");
    }

    private static void testAddEmployee() {
        LOGGER.info("Start");
        Employee employee = new Employee();
        employee.setName("Erin Walsh");
        employee.setSalary(58000.00);
        employee.setPermanent(true);
        employee.setDateOfBirth(LocalDate.of(1993, 6, 15));

        Department department = departmentService.get(1);
        employee.setDepartment(department);

        employeeService.save(employee);
        LOGGER.debug("Employee saved:{}", employee);
        LOGGER.info("End");
    }

    private static void testUpdateEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(1);

        Department newDepartment = departmentService.get(2);
        employee.setDepartment(newDepartment);

        employeeService.save(employee);
        LOGGER.debug("Employee updated:{}", employee);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2, Hands on 5 - @OneToMany (Department -> Employees)
    // =====================================================================

    private static void testGetDepartment() {
        LOGGER.info("Start");
        Department department = departmentService.get(1);
        LOGGER.debug("Department:{}", department);
        LOGGER.debug("Employees:{}", department.getEmployeeList());
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2, Hands on 6 - @ManyToMany (Employee <-> Skill)
    // =====================================================================

    private static void testAddSkillToEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(2);
        Skill skill = skillService.get(2);

        Set<Skill> skillList = employee.getSkillList();
        skillList.add(skill);
        employee.setSkillList(skillList);

        employeeService.save(employee);
        LOGGER.debug("Employee with new skill:{}", employee);
        LOGGER.info("End");
    }
}
