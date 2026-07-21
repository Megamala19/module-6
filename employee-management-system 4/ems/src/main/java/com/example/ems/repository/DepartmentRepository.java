package com.example.ems.repository;

import com.example.ems.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Derived query method - Spring Data JPA builds the query from the name.
    Optional<Department> findByName(String name);

    boolean existsByName(String name);
}
