package com.example.ormlearn;

import com.example.ormlearn.entity.Attempt;
import com.example.ormlearn.entity.AttemptQuestion;
import com.example.ormlearn.entity.Employee;
import com.example.ormlearn.entity.Options;
import com.example.ormlearn.service.AttemptService;
import com.example.ormlearn.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttemptService attemptService;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Uncomment whichever hands-on exercise you want to try. They are
        // left commented out by default since they need matching data in
        // the database first (see README.md).

        // testGetAllPermanentEmployees();
        // testGetAverageSalary();
        // testGetAllEmployeesNative();
        // testGetAttempt();
    }

    // ------------------------------------------------------------
    // Hands-on 2: HQL with fetch to avoid N+1 queries
    // ------------------------------------------------------------
    public void testGetAllPermanentEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllPermanentEmployees();
        LOGGER.debug("Permanent Employees:{}", employees);
        employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
        LOGGER.info("End");
    }

    // ------------------------------------------------------------
    // Hands-on 4: aggregate function (AVG) in HQL
    // ------------------------------------------------------------
    public void testGetAverageSalary() {
        LOGGER.info("Start");
        double overallAverage = employeeService.getAverageSalary();
        LOGGER.debug("Average salary (all employees): {}", overallAverage);

        double deptAverage = employeeService.getAverageSalary(1);
        LOGGER.debug("Average salary (department 1): {}", deptAverage);
        LOGGER.info("End");
    }

    // ------------------------------------------------------------
    // Hands-on 5: Native Query
    // ------------------------------------------------------------
    public void testGetAllEmployeesNative() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllEmployeesNative();
        LOGGER.debug("All Employees (native query):{}", employees);
        LOGGER.info("End");
    }

    // ------------------------------------------------------------
    // Hands-on 3: multi-table fetch join, printed in the format shown
    // in the hands-on doc: question text, then each option with its
    // score and whether it was the one the user selected.
    // ------------------------------------------------------------
    public void testGetAttempt() {
        LOGGER.info("Start");
        int userId = 1;
        int attemptId = 1;
        Attempt attempt = attemptService.getAttempt(userId, attemptId);

        if (attempt == null) {
            LOGGER.debug("No attempt found for userId={}, attemptId={}", userId, attemptId);
            LOGGER.info("End");
            return;
        }

        for (AttemptQuestion aq : attempt.getAttemptQuestionList()) {
            System.out.println(aq.getQuestion().getQuestionText());

            // The option(s) the user picked for this question
            List<Integer> selectedOptionIds = aq.getAttemptOptionList().stream()
                    .map(ao -> ao.getSelectedOption().getId())
                    .toList();

            int index = 1;
            for (Options option : aq.getQuestion().getOptionList()) {
                boolean wasSelected = selectedOptionIds.contains(option.getId());
                System.out.println(" " + index + ") " + option.getOptionText()
                        + "\t" + option.getScore() + "\t" + wasSelected);
                index++;
            }
        }
        LOGGER.info("End");
    }
}
