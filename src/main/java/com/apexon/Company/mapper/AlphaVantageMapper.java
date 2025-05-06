package com.apexon.Company.mapper;

import com.apexon.Company.model.AlphaVantageMatch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class AlphaVantageMapper {
    public static List<AlphaVantageMatch> parseMatches(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode matches = root.get("bestMatches");

        List<AlphaVantageMatch> result = new ArrayList<>();
        for (JsonNode match : matches) {
            AlphaVantageMatch dto = new AlphaVantageMatch();
            dto.setSymbol(match.get("1. symbol").asText());
            dto.setName(match.get("2. name").asText());
            dto.setAssetType(match.get("3. type").asText());
            dto.setExchange(match.get("4. region").asText()); // mapping 'region' as 'exchange'
            result.add(dto);
        }
        return result;
    }
}
