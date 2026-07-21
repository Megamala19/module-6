package com.example.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Exercise 2: Employee entity with id, name, email, department.
 *
 * Exercise 5 (Named Queries): declared below via @NamedQuery. Spring Data
 * JPA will use a named query automatically instead of deriving one from the
 * method name, as long as the name follows the "EntityName.methodName"
 * convention and the repository method's name matches exactly - see
 * EmployeeRepository.findByDepartmentName(...).
 *
 * Exercise 10 (Hibernate-specific features): @DynamicInsert / @DynamicUpdate
 * tell Hibernate to only include changed/non-null columns in the generated
 * INSERT/UPDATE statement instead of every column - useful on wide tables
 * with many nullable columns.
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@NamedQueries({
        @NamedQuery(
                name = "Employee.findByDepartmentName",
                query = "SELECT e FROM Employee e WHERE e.department.name = :departmentName"
        )
})
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
