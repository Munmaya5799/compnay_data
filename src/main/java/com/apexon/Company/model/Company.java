package com.apexon.Company.model;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String symbol;
    private String assetType;
    private String currency;
    private String name;
}

