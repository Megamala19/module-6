# Employee Management System — Spring Data JPA & Hibernate Hands-On

A runnable Spring Boot + H2 project implementing all 10 exercises from the
"Spring Data JPA and Hibernate" training material.

## Project structure

```
src/main/java/com/example/ems/
├── EmployeeManagementSystemApplication.java   # main class, @EnableJpaAuditing
├── entity/
│   ├── Auditable.java        # Ex.7 base class: createdBy/createdDate/etc.
│   ├── Department.java       # Ex.2
│   └── Employee.java         # Ex.2, Ex.5 (@NamedQuery), Ex.10 (@DynamicInsert/@DynamicUpdate)
├── repository/
│   ├── DepartmentRepository.java
│   └── EmployeeRepository.java   # Ex.3, Ex.5, Ex.6, Ex.8
├── service/
│   ├── DepartmentService.java
│   └── EmployeeService.java      # Ex.4, Ex.5, Ex.6, Ex.8, Ex.10
├── controller/
│   ├── DepartmentController.java # Ex.4
│   └── EmployeeController.java   # Ex.4, Ex.5, Ex.6, Ex.8, Ex.10
├── projection/
│   ├── EmployeeSummary.java      # Ex.8 interface-based projection
│   └── EmployeeNameOnly.java     # Ex.8 class-based (DTO) projection
├── config/
│   ├── AuditingConfig.java           # Ex.7 AuditorAware bean
│   └── SecondaryDataSourceConfig.java # Ex.9 second datasource
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java

src/main/resources/
├── application.properties   # Ex.1, Ex.9, Ex.10 configuration
└── data.sql                 # seed data (departments + employees)
```

## Exercise coverage

| # | Topic | Where |
|---|-------|-------|
| 1 | Project setup, H2 datasource | `pom.xml`, `application.properties` |
| 2 | JPA entities, Department ↔ Employee one-to-many | `entity/Department.java`, `entity/Employee.java` |
| 3 | `JpaRepository` interfaces, derived query methods | `repository/*` |
| 4 | CRUD via REST controllers | `controller/*`, `service/*` |
| 5 | `@Query`, named queries (`@NamedQuery`) | `EmployeeRepository`, `Employee` entity |
| 6 | Pagination and sorting | `EmployeeController#getEmployeesPaged`, `#searchPaged` |
| 7 | Entity auditing (`@CreatedDate`, `@LastModifiedDate`, etc.) | `entity/Auditable.java`, `config/AuditingConfig.java` |
| 8 | Interface- and class-based projections | `projection/*`, `EmployeeRepository` |
| 9 | Multiple datasource configuration | `config/SecondaryDataSourceConfig.java` |
| 10 | Hibernate-specific annotations & batch processing | `Employee` entity (`@DynamicInsert/@DynamicUpdate`), `application.properties` batch settings, `EmployeeService#bulkCreateEmployees` |

## Running it locally

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080` with an in-memory H2 database,
pre-seeded with 3 departments and 4 employees (see `data.sql`). Browse the
data directly at `http://localhost:8080/h2-console` (JDBC URL
`jdbc:h2:mem:testdb`, user `sa`, password `password`).

### Example requests

```bash
# Exercise 4: CRUD
curl http://localhost:8080/api/employees
curl http://localhost:8080/api/departments/1
curl -X POST http://localhost:8080/api/departments -H "Content-Type: application/json" -d '{"name":"Marketing"}'

# Exercise 5: query methods / named query
curl "http://localhost:8080/api/employees/search?name=ali"
curl http://localhost:8080/api/employees/department-name/Engineering

# Exercise 6: pagination & sorting
curl "http://localhost:8080/api/employees/paged?page=0&size=2&sortBy=name&direction=asc"

# Exercise 8: projections
curl http://localhost:8080/api/employees/summaries
curl http://localhost:8080/api/employees/names-only

# Exercise 10: bulk insert (batched by Hibernate per application.properties)
curl -X POST http://localhost:8080/api/employees/bulk -H "Content-Type: application/json" \
  -d '[{"name":"New Hire 1","email":"nh1@example.com"},{"name":"New Hire 2","email":"nh2@example.com"}]'
```

## Uploading this to GitHub

From inside this folder:

```bash
git init
git add .
git commit -m "Employee Management System: Spring Data JPA & Hibernate hands-on"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

Or on GitHub.com: create a new empty repository, then drag this folder in via
"uploading an existing file", or point GitHub Desktop at this folder.
