package com.apexon.Company.controller;
import com.apexon.Company.model.Company;
import com.apexon.Company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{symbol}")
    public Company getCompanyDetails(@PathVariable String symbol) throws Exception {
        return companyService.fetchAndStoreCompanyDetails(symbol);
    }
}

