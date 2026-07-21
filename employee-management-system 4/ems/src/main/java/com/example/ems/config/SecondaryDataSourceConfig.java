package com.example.ems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Exercise 9: Customizing Data Source Configuration.
 *
 * Spring Boot auto-configures ONE primary datasource from
 * application.properties (spring.datasource.*) and wires it into JPA -
 * that's what Employee/Department use.
 *
 * This class shows how to stand up a *second*, independent datasource
 * (a "reporting" database) alongside it, driven by the separate
 * reporting.datasource.* properties. It's deliberately kept outside JPA
 * (plain JdbcTemplate) to avoid the added complexity of a second
 * EntityManagerFactory/TransactionManager pair - wire that up instead if
 * you need JPA entities backed by this second datasource too.
 */
@Configuration
public class SecondaryDataSourceConfig {

    @Value("${reporting.datasource.url}")
    private String url;

    @Value("${reporting.datasource.driverClassName}")
    private String driverClassName;

    @Value("${reporting.datasource.username}")
    private String username;

    @Value("${reporting.datasource.password}")
    private String password;

    @Bean(name = "reportingDataSource")
    public DataSource reportingDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "reportingJdbcTemplate")
    public JdbcTemplate reportingJdbcTemplate(@org.springframework.beans.factory.annotation.Qualifier("reportingDataSource") DataSource reportingDataSource) {
        return new JdbcTemplate(reportingDataSource);
    }
}
