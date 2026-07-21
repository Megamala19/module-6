# orm-learn — Spring Data JPA Hands-On

A runnable Spring Boot project implementing the full **Spring Data JPA hands-on** exercise
series: `Country` CRUD, Query Methods, a `Stock` entity with derived queries, and the
`Employee` / `Department` / `Skill` relationship mappings (`@ManyToOne`, `@OneToMany`,
`@ManyToMany`).

## What's implemented

| Hands on | Feature |
|---|---|
| Doc 1 / 1 | Base Spring Boot + Spring Data JPA project, `OrmLearnApplication` with logging |
| Doc 1 / 5 | `Country` entity, `CountryRepository`, `CountryService.getAllCountries()`, full country reference data |
| Doc 1 / 6 | `CountryService.findCountryByCode()` + `CountryNotFoundException` |
| Doc 1 / 7 | `CountryService.addCountry()` |
| Doc 1 / 8 | `CountryService.updateCountry()` |
| Doc 1 / 9 | `CountryService.deleteCountry()` |
| Doc 2 / 1 | `CountryRepository` Query Methods — contains, contains + sorted, starts-with (search box / alphabet index) |
| Doc 2 / 2 | `Stock` entity, `StockRepository` Query Methods — date range, greater-than, top-N by volume/close |
| Doc 2 / 3 | `Employee`, `Department`, `Skill` entities + repositories |
| Doc 2 / 4 | `@ManyToOne` / `@JoinColumn` (Employee → Department), `EmployeeService` / `DepartmentService` get/save |
| Doc 2 / 5 | `@OneToMany(mappedBy=...)` (Department → Employees), EAGER fetch |
| Doc 2 / 6 | `@ManyToMany` / `@JoinTable` (Employee ↔ Skill), EAGER fetch |

## Project structure

```
src/main/java/com/cognizant/ormlearn/
├── OrmLearnApplication.java              # main() + test methods for every operation
├── model/
│   ├── Country.java                      # @Entity mapped to the `country` table
│   ├── Stock.java                        # @Entity mapped to the `stock` table
│   ├── Employee.java                     # @ManyToOne Department, @ManyToMany Skill
│   ├── Department.java                   # @OneToMany Employee
│   └── Skill.java                        # @ManyToMany Employee (inverse side)
├── repository/
│   ├── CountryRepository.java            # JpaRepository + Query Methods
│   ├── StockRepository.java              # JpaRepository + Query Methods
│   ├── EmployeeRepository.java
│   ├── DepartmentRepository.java
│   └── SkillRepository.java
└── service/
    ├── CountryService.java               # @Transactional business methods
    ├── StockService.java
    ├── EmployeeService.java              # get() / save()
    ├── DepartmentService.java            # get() / save()
    ├── SkillService.java                 # get() / save()
    └── exception/CountryNotFoundException.java

src/main/resources/
├── application.properties        # default profile — H2 in-memory, runs out of the box
├── application-mysql.properties  # MySQL profile, matching the original hands-on doc
├── schema-mysql.sql              # DDL to run manually before using the mysql profile
└── data.sql                      # country reference data (249 rows), sample stock &
                                   # payroll (department/skill/employee) data, loads on startup
```

## Quick start (H2, no setup required)

```bash
mvn spring-boot:run
```

The app starts against an in-memory H2 database, auto-creates every table from the JPA
entities, loads the reference/sample data from `data.sql`, then runs through every
hands-on operation across all three domains (country, stock, payroll) and logs the
results.

H2 console (while the app is running): http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:mem:ormlearn`, user `sa`, empty password.

## Running against real MySQL (as in the original hands-on docs)

1. Install MySQL Server 8.0.
2. Create the schema and tables:
   ```sql
   mysql -u root -p
   mysql> create schema ormlearn;
   mysql> use ormlearn;
   mysql> source src/main/resources/schema-mysql.sql;
   mysql> source src/main/resources/data.sql;
   ```
3. Update the username/password in `application-mysql.properties` if they differ from `root`/`root`.
4. Run with the `mysql` profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

Note: the original hands-on doc has you download the full year of stock data from a
CSV and generate its own insert script; here `data.sql` ships with the sample rows the
doc itself uses as expected query output (Facebook Sept 2019, Google > 1250, etc.) so
the queries are demonstrable without that external file.

## Build a jar

```bash
mvn clean package
java -jar target/orm-learn-0.0.1-SNAPSHOT.jar
```

## Requirements

- Java 11+
- Maven 3.6+
- MySQL Server 8.0 (only if using the `mysql` profile)
