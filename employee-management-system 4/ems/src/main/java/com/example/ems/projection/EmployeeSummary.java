package com.example.ems.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Exercise 8: interface-based projection. Spring Data JPA generates a
 * proxy at runtime that only pulls the columns needed for these getters,
 * instead of the whole Employee entity.
 *
 * getId()/getName() are a "closed" projection (matched directly to Employee
 * fields). getDepartmentName() is an "open" projection - it needs the
 * @Value SpEL expression because it reaches into a nested association.
 */
public interface EmployeeSummary {

    Long getId();

    String getName();

    @Value("#{target.department != null ? target.department.name : null}")
    String getDepartmentName();
}
