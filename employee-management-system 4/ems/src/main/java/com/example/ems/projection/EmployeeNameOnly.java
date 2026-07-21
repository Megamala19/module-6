package com.example.ems.projection;

import lombok.Getter;

/**
 * Exercise 8: class-based projection, populated via a JPQL constructor
 * expression (see EmployeeRepository.findNameOnlyBy()). Unlike the
 * interface-based projection, this is a plain DTO Spring Data fills by
 * calling this exact constructor.
 */
@Getter
public class EmployeeNameOnly {

    private final Long id;
    private final String name;

    public EmployeeNameOnly(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
