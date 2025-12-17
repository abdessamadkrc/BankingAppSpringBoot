package com.bankingapp.transactionservice.client;

import com.bankingapp.transactionservice.model.Compte;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CompteService", url = "http://localhost:8095")
public interface CompteRestClient {

    @GetMapping("/comptes/{id}")
    Compte getCompte(@PathVariable("id") Long id);

    @PutMapping("/comptes/{id}")
    Compte updateCompte(@PathVariable("id") Long id, @RequestBody Compte c);
}
