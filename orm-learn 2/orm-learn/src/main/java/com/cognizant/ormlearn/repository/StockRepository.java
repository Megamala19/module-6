package com.cognizant.ormlearn.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // Hands on 2: all stock details of a given code between two dates (e.g. Facebook, Sep 2019)
    List<Stock> findByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);

    // Hands on 2: all stock details where the opening price was greater than the given value
    List<Stock> findByCodeAndOpenGreaterThan(String code, BigDecimal open);

    // Hands on 2: top 3 dates with the highest volume of transactions for a given code
    List<Stock> findTop3ByCodeOrderByVolumeDesc(String code);

    // Hands on 2: top 3 dates with the lowest closing price for a given code
    List<Stock> findTop3ByCodeOrderByCloseAsc(String code);
}
