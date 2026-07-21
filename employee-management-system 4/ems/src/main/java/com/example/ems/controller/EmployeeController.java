package com.example.ems.controller;

import com.example.ems.entity.Employee;
import com.example.ems.projection.EmployeeNameOnly;
import com.example.ems.projection.EmployeeSummary;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // -------------------- Exercise 4: CRUD --------------------

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody Employee employee,
                                    @RequestParam(required = false) Long departmentId) {
        return employeeService.createEmployee(employee, departmentId);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                    @Valid @RequestBody Employee employee,
                                    @RequestParam(required = false) Long departmentId) {
        return employeeService.updateEmployee(id, employee, departmentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------- Exercise 5: query methods --------------------

    @GetMapping("/search")
    public List<Employee> search(@RequestParam String name) {
        return employeeService.searchByName(name);
    }

    @GetMapping("/department/{departmentId}/sorted")
    public List<Employee> getSortedByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesInDepartmentSortedByName(departmentId);
    }

    @GetMapping("/department-name/{departmentName}")
    public List<Employee> getByDepartmentName(@PathVariable String departmentName) {
        return employeeService.getEmployeesByDepartmentName(departmentName);
    }

    // -------------------- Exercise 6: pagination & sorting --------------------

    @GetMapping("/paged")
    public Page<Employee> getEmployeesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeService.getEmployeesPaged(pageable);
    }

    @GetMapping("/search/paged")
    public Page<Employee> searchPaged(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeService.searchByNamePaged(name, pageable);
    }

    // -------------------- Exercise 8: projections --------------------

    @GetMapping("/summaries")
    public List<EmployeeSummary> getSummaries() {
        return employeeService.getEmployeeSummaries();
    }

    @GetMapping("/names-only")
    public List<EmployeeNameOnly> getNamesOnly() {
        return employeeService.getEmployeeNamesOnly();
    }

    // -------------------- Exercise 10: batch insert demo --------------------

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> bulkCreate(@RequestBody List<Employee> employees) {
        return employeeService.bulkCreateEmployees(employees);
    }
}
