package com.apexon.Company.mapper;

import com.apexon.Company.model.AlphaVantageMatch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class AlphaVantageMapper {
    public static List<AlphaVantageMatch> parseMatches(String json) {
        List<AlphaVantageMatch> result = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode matches = root.get("bestMatches");

            if (matches != null && matches.isArray()) {
                for (JsonNode match : matches) {
                    AlphaVantageMatch dto = new AlphaVantageMatch();
                    dto.setSymbol(match.get("1. symbol").asText());
                    dto.setName(match.get("2. name").asText());
                    dto.setAssetType(match.get("3. type").asText());
                    dto.setCurrency(match.get("8. currency").asText());
                    result.add(dto);
                }
            } else {
                System.out.println("No 'bestMatches' found in response.");
            }
        } catch (Exception e) {
            System.err.println("Failed to parse AlphaVantage JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}