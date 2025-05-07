package com.apexon.Company.model;
import lombok.Data;

@Data
public class AlphaVantageMatch {
    private String symbol;
    private String name;
    private String assetType;
    //private String exchange;
    private String currency;;
}

