package com.bankingapp.transactionservice.model;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    private Long id;
    private String type;
    private Double solde;
}
