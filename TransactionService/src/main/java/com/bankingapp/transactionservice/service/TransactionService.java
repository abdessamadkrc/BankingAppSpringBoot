package com.bankingapp.transactionservice.service;

import com.bankingapp.transactionservice.client.CompteRestClient;
import com.bankingapp.transactionservice.client.ReportingRestClient;
import com.bankingapp.transactionservice.entity.Transaction;
import com.bankingapp.transactionservice.model.Compte;
import com.bankingapp.transactionservice.model.ExchangeRate;
import com.bankingapp.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    private final CompteRestClient compteClient;
    private final ReportingRestClient reportingClient;
    private final TransactionRepository transactionRepository;

    public TransactionService(CompteRestClient compteClient, ReportingRestClient reportingClient, TransactionRepository transactionRepository) {
        this.compteClient = compteClient;
        this.reportingClient = reportingClient;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(Long src, Long dest, Double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        
        // Get source and destination accounts
        Compte srcC = compteClient.getCompte(src);
        Compte destC = compteClient.getCompte(dest);
        
        // Check sufficient funds in source currency
        if (srcC.getSolde() < amount) throw new IllegalArgumentException("Insufficient funds");
        
        // Deduct from source account
        srcC.setSolde(srcC.getSolde() - amount);
        
        // Calculate converted amount for destination account
        Double convertedAmount = amount;
        String sourceCurrency = srcC.getType();
        String destCurrency = destC.getType();
        
        // If currencies are different, apply conversion
        if (!sourceCurrency.equals(destCurrency)) {
            try {
                ExchangeRate rate = reportingClient.getExchangeRate(sourceCurrency, destCurrency);
                convertedAmount = amount * rate.getRate();
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to get exchange rate from " + sourceCurrency + " to " + destCurrency);
            }
        }
        
        // Add converted amount to destination account
        destC.setSolde(destC.getSolde() + convertedAmount);
        
        // Update both accounts
        compteClient.updateCompte(src, srcC);
        compteClient.updateCompte(dest, destC);
        
        // Save transaction record (amount in source currency)
        Transaction t = Transaction.builder()
            .sourceId(src)
            .destinationId(dest)
            .montant(amount)
            .dateTransaction(Instant.now())
            .build();
        transactionRepository.save(t);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findBySourceIdOrDestinationId(accountId, accountId);
    }
}
