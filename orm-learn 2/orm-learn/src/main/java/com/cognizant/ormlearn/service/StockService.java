package com.cognizant.ormlearn.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public List<Stock> findByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate) {
        return stockRepository.findByCodeAndDateBetween(code, startDate, endDate);
    }

    @Transactional
    public List<Stock> findByCodeAndOpenGreaterThan(String code, BigDecimal open) {
        return stockRepository.findByCodeAndOpenGreaterThan(code, open);
    }

    @Transactional
    public List<Stock> findTop3ByCodeOrderByVolumeDesc(String code) {
        return stockRepository.findTop3ByCodeOrderByVolumeDesc(code);
    }

    @Transactional
    public List<Stock> findTop3ByCodeOrderByCloseAsc(String code) {
        return stockRepository.findTop3ByCodeOrderByCloseAsc(code);
    }
}
