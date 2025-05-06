package com.apexon.Company.service;
import com.apexon.Company.mapper.AlphaVantageMapper;
import com.apexon.Company.model.AlphaVantageMatch;
import com.apexon.Company.model.AlphaVantageOverview;
import com.apexon.Company.model.AlphaVantageResponse;
import com.apexon.Company.model.Company;
import com.apexon.Company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public Company fetchAndStoreCompanyDetails(String symbol) throws Exception {
        String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + symbol + "&apikey=" + apiKey;
       // String overviewUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;

        // Fetch company data from AlphaVantage API
        String alphaRespJson = restTemplate.getForObject(url, String.class);
        List<AlphaVantageMatch> alphaVantageMatches = AlphaVantageMapper.parseMatches(alphaRespJson);
        AlphaVantageResponse response = new AlphaVantageResponse();
        response.setBestMatches(alphaVantageMatches);

        if (response != null && response.getBestMatches() != null && !response.getBestMatches().isEmpty()) {
            AlphaVantageMatch match = response.getBestMatches().getFirst();

            String overviewUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            AlphaVantageOverview overviewResponse = restTemplate.getForObject(overviewUrl, AlphaVantageOverview.class);

            Company company = new Company();
            company.setSymbol(match.getSymbol());
            company.setAssetType(match.getAssetType());
            company.setExchange(overviewResponse.getExchange());
            company.setCurrency(overviewResponse.getCurrency());
            company.setName(overviewResponse.getName());



            return companyRepository.save(company);
        }

        return null;
    }
}
