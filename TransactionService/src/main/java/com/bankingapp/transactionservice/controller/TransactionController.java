package com.bankingapp.transactionservice.controller;

import com.bankingapp.transactionservice.entity.Transaction;
import com.bankingapp.transactionservice.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) { this.service = service; }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam Long src, @RequestParam Long dest, @RequestParam Double amount) {
        service.transfer(src, dest, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.getTransactionsByAccount(accountId));
    }
}
