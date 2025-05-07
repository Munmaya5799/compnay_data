package com.apexon.Company.repository;

import com.apexon.Company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findBySymbol(String symbol);

}

