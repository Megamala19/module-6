package com.example.ormlearn.repository;

import com.example.ormlearn.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // ----------------------------------------------------------------
    // Hands-on 2: Get all permanent employees using HQL, including their
    // department and skill list, optimized with 'fetch' so a single SQL
    // query is generated instead of one query per relationship.
    // ----------------------------------------------------------------
    @Query(value = "SELECT e FROM Employee e " +
            "left join fetch e.department d " +
            "left join fetch e.skillList " +
            "WHERE e.permanent = true")
    List<Employee> getAllPermanentEmployees();

    // ----------------------------------------------------------------
    // Hands-on 4: Average salary across all employees, and average
    // salary filtered by department id using a named parameter.
    // ----------------------------------------------------------------
    @Query(value = "SELECT AVG(e.salary) FROM Employee e")
    double getAverageSalary();

    @Query(value = "SELECT AVG(e.salary) FROM Employee e where e.department.id = :id")
    double getAverageSalary(@Param("id") int id);

    // ----------------------------------------------------------------
    // Hands-on 5: Native Query - direct SQL instead of HQL/JPQL. Use
    // sparingly; native queries are tied to the specific database dialect
    // and reduce portability.
    // ----------------------------------------------------------------
    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> getAllEmployeesNative();
}
