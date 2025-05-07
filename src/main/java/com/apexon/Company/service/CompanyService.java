package com.apexon.Company.service;
import com.apexon.Company.mapper.AlphaVantageMapper;
import com.apexon.Company.model.AlphaVantageMatch;
import com.apexon.Company.model.Company;
import com.apexon.Company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final CompanyRepository companyRepository;

    public CompanyService(RestTemplate restTemplate, CompanyRepository companyRepository) {
        this.restTemplate = restTemplate;
        this.companyRepository = companyRepository;
    }
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company fetchAndStoreCompanyDetails(String symbol) {

        try {
            // Build the URL to call AlphaVantage API's SYMBOL_SEARCH endpoint
            String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + symbol + "&apikey=" + apiKey;

            // Fetch company data from AlphaVantage API
            String alphaRespJson = restTemplate.getForObject(url, String.class);

            // Parse the API response into a list of matches
            List<AlphaVantageMatch> alphaVantageMatches = AlphaVantageMapper.parseMatches(alphaRespJson);

            // Check if matches are found
            if (alphaVantageMatches != null && !alphaVantageMatches.isEmpty()) {
                AlphaVantageMatch match = alphaVantageMatches.get(0); // Get the first match

                // Check if the company already exists in the database by symbol
                Optional<Company> existingCompany = companyRepository.findBySymbol(match.getSymbol());
                if (existingCompany.isPresent()) {
                    System.out.println("Company already exists in DB: " + match.getSymbol());
                    return existingCompany.get(); // Return the existing company if found
                }

                // Create a new Company object to save
                Company company = new Company();
                company.setSymbol(match.getSymbol());
                company.setAssetType(match.getAssetType());
                company.setName(match.getName());
                company.setCurrency(match.getCurrency());

                // Save the company to the database
                return companyRepository.save(company);
            } else {
                System.out.println("No company matches found for symbol: " + symbol);
            }
        } catch (Exception e) {
            // Log and handle any exception that occurs during the process
            System.err.println("Error fetching or storing company details for symbol " + symbol + ": " + e.getMessage());
            e.printStackTrace();
        }

        return null;  // Return null if no valid company data is found or an error occurred
    }
}