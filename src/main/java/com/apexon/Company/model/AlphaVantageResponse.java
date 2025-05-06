package com.apexon.Company.model;
import lombok.Data;

import java.util.List;

@Data
public class AlphaVantageResponse {
    private List<AlphaVantageMatch> bestMatches;
}
