package com.example.ems.service;

import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.projection.EmployeeNameOnly;
import com.example.ems.projection.EmployeeSummary;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    // -------------------- Exercise 4: CRUD --------------------

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee, Long departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
            employee.setDepartment(department);
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updated, Long departmentId) {
        Employee existing = getEmployeeById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
            existing.setDepartment(department);
        }
        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }

    // -------------------- Exercise 5: query methods --------------------

    public List<Employee> searchByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> getEmployeesInDepartmentSortedByName(Long departmentId) {
        return employeeRepository.findEmployeesInDepartmentSortedByName(departmentId);
    }

    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }

    // -------------------- Exercise 6: pagination & sorting --------------------

    public Page<Employee> getEmployeesPaged(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> searchByNamePaged(String name, Pageable pageable) {
        return employeeRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // -------------------- Exercise 8: projections --------------------

    public List<EmployeeSummary> getEmployeeSummaries() {
        return employeeRepository.findAllProjectedBy();
    }

    public List<EmployeeNameOnly> getEmployeeNamesOnly() {
        return employeeRepository.findNameOnlyBy();
    }

    // -------------------- Exercise 10: Hibernate batch processing --------------------

    /**
     * Bulk-inserts employees in one transaction. Because Employee uses
     * GenerationType.SEQUENCE (not IDENTITY) and application.properties sets
     * hibernate.jdbc.batch_size, Hibernate groups these INSERT statements
     * into batches instead of sending one round-trip per row.
     */
    public List<Employee> bulkCreateEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }
}
