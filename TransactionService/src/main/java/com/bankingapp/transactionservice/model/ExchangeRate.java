package com.bankingapp.transactionservice.model;

import lombok.Data;

@Data
public class ExchangeRate {
    private String from;
    private String to;
    private Double rate;
}
