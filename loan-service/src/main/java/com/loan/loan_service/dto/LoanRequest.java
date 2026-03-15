package com.loan.loan_service.dto;

import lombok.Data;

@Data
public class LoanRequest {

    private Double amount;
    private String purpose;
    private Integer tenure;

}