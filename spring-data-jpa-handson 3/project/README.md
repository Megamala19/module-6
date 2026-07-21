# Spring Data JPA Hands-On — HQL, JPQL, Native Query & Criteria Query

A runnable Spring Boot project implementing the hands-on exercises from the
"Spring Data JPA" training material: HQL vs JPQL, `@Query`, fetch joins,
aggregate functions, native queries, and a pointer to Criteria Query.

## Project structure

```
src/main/java/com/example/ormlearn/
├── OrmLearnApplication.java      # main class + test methods for each hands-on
├── entity/                       # Employee, Department, Skill, User, Question,
│                                  # Options, Attempt, AttemptQuestion, AttemptOption
├── repository/
│   ├── EmployeeRepository.java   # Hands-on 2, 4, 5
│   └── AttemptRepository.java    # Hands-on 3
└── service/
    ├── EmployeeService.java
    └── AttemptService.java

src/main/resources/
├── application.properties        # MySQL connection settings
└── schema.sql                    # table definitions + sample data
```

## Hands-on coverage

| # | Topic | Where |
|---|-------|-------|
| 2 | Get all permanent employees with HQL, using `fetch` to avoid N+1 queries | `EmployeeRepository.getAllPermanentEmployees()` |
| 3 | Fetch quiz attempt details across 6 joined tables | `AttemptRepository.getAttempt()` |
| 4 | Average salary using an aggregate function, with and without a `@Param` filter | `EmployeeRepository.getAverageSalary(...)` |
| 5 | Native SQL query via `nativeQuery = true` | `EmployeeRepository.getAllEmployeesNative()` |
| 6 | Criteria Query | See reference link in the original hands-on doc; not implemented as code since it depends on your own dynamic search screen |

## Running it locally

1. Create a MySQL schema (default name used in `application.properties` is `ormlearn`):
   ```sql
   CREATE DATABASE ormlearn;
   ```
2. Load the tables and sample data:
   ```bash
   mysql -u root -p ormlearn < src/main/resources/schema.sql
   ```
3. Update `src/main/resources/application.properties` with your own MySQL username/password.
4. Uncomment whichever `test...()` method you want to try inside `OrmLearnApplication.run(...)`.
5. Run it:
   ```bash
   ./mvnw spring-boot:run
   ```
   (or `mvn spring-boot:run` if you have Maven installed globally)

## Uploading this to GitHub

From inside this folder:

```bash
git init
git add .
git commit -m "Spring Data JPA hands-on: HQL, JPQL, native query"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

Or, on GitHub.com: create a new empty repository, then use the "uploading an
existing file" link on the repo page and drag this whole folder in (as a zip
first, then extract, or use GitHub Desktop to point at this folder directly).
