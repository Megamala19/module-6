package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Query method: find list of countries matching a partial country name
    List<Country> findByNameContainingIgnoreCase(String partialName);

    // Hands on 1 (part 2): search-as-you-type - countries containing the given text
    List<Country> findByNameContaining(String partialName);

    // Hands on 1 (part 2): same as above, but returned in ascending name order
    List<Country> findByNameContainingOrderByNameAsc(String partialName);

    // Hands on 1 (part 2): alphabet index - countries whose name starts with the given letter
    List<Country> findByNameStartingWith(String letter);
}
