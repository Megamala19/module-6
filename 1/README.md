# orm-learn — Spring Data JPA Hands-On

A runnable Spring Boot project implementing the full **Spring Data JPA hands-on** exercises
(Hands on 1, 5, 6, 7, 8, 9) from the training document: a `Country` entity backed by
Spring Data JPA with find-by-code, add, update, delete, and partial-name search.

## What's implemented

| Hands on | Feature |
|---|---|
| 1 | Base Spring Boot + Spring Data JPA project, `OrmLearnApplication` with logging |
| 5 | `Country` entity, `CountryRepository`, `CountryService.getAllCountries()`, full country reference data |
| 6 | `CountryService.findCountryByCode()` + `CountryNotFoundException` |
| 7 | `CountryService.addCountry()` |
| 8 | `CountryService.updateCountry()` |
| 9 | `CountryService.deleteCountry()` |
| bonus | `findCountriesByPartialName()` — find countries matching a partial name |

## Project structure

```
src/main/java/com/cognizant/ormlearn/
├── OrmLearnApplication.java              # main() + test methods for every operation
├── model/Country.java                    # @Entity mapped to the `country` table
├── repository/CountryRepository.java     # JpaRepository<Country, String>
└── service/
    ├── CountryService.java               # @Transactional business methods
    └── exception/CountryNotFoundException.java

src/main/resources/
├── application.properties        # default profile — H2 in-memory, runs out of the box
├── application-mysql.properties  # MySQL profile, matching the original hands-on doc
├── schema-mysql.sql              # DDL to run manually before using the mysql profile
└── data.sql                      # full country reference list (249 rows), loads on startup
```

## Quick start (H2, no setup required)

```bash
mvn spring-boot:run
```

The app starts against an in-memory H2 database, auto-creates the `country` table, loads
all 249 countries from `data.sql`, then runs through each hands-on operation
(list, find by code, add, update, find by partial name, delete) and logs the results.

H2 console (while the app is running): http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:mem:ormlearn`, user `sa`, empty password.

## Running against real MySQL (as in the original hands-on doc)

1. Install MySQL Server 8.0.
2. Create the schema and table:
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

## Build a jar

```bash
mvn clean package
java -jar target/orm-learn-0.0.1-SNAPSHOT.jar
```

## Requirements

- Java 11+
- Maven 3.6+
- MySQL Server 8.0 (only if using the `mysql` profile)
