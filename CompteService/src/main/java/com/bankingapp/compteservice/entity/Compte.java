package com.bankingapp.compteservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comptes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // Currency code: USD, EUR, MAD, etc.
    private Double solde;
}
