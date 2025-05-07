package com.apexon.Company.model;
import lombok.Data;

@Data
public class AlphaVantageOverview {
    private String symbol;
    private String assetType;
    private String name;
    private String currency;
}