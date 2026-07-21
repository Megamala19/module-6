package com.example.ems.repository;

import com.example.ems.entity.Employee;
import com.example.ems.projection.EmployeeNameOnly;
import com.example.ems.projection.EmployeeSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ----------------------------------------------------------------
    // Exercise 3 & 5: derived query methods (built purely from the
    // method name - no query body needed).
    // ----------------------------------------------------------------
    List<Employee> findByNameContainingIgnoreCase(String name);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentId(Long departmentId);

    // ----------------------------------------------------------------
    // Exercise 5: custom query using @Query (JPQL).
    // ----------------------------------------------------------------
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId ORDER BY e.name ASC")
    List<Employee> findEmployeesInDepartmentSortedByName(@Param("departmentId") Long departmentId);

    // ----------------------------------------------------------------
    // Exercise 5: named query. No @Query needed here - the method name
    // "findByDepartmentName" matches the @NamedQuery declared on Employee
    // ("Employee.findByDepartmentName"), so Spring Data JPA uses that
    // named query instead of deriving one from the method name.
    // ----------------------------------------------------------------
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    // ----------------------------------------------------------------
    // Exercise 6: pagination and sorting. Pageable already carries the
    // page number, page size, and sort order - the caller builds it
    // (see EmployeeController) and Spring Data JPA does the rest.
    // ----------------------------------------------------------------
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // ----------------------------------------------------------------
    // Exercise 8: projections.
    // Interface-based - only id/name/department.name are fetched.
    // ----------------------------------------------------------------
    List<EmployeeSummary> findByDepartmentId(Long departmentId, Class<EmployeeSummary> type);

    List<EmployeeSummary> findAllProjectedBy();

    // Class-based (DTO) projection via a JPQL constructor expression.
    @Query("SELECT new com.example.ems.projection.EmployeeNameOnly(e.id, e.name) FROM Employee e")
    List<EmployeeNameOnly> findNameOnlyBy();
}
