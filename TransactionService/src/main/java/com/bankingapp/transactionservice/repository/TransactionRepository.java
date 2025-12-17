package com.bankingapp.transactionservice.repository;

import com.bankingapp.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceIdOrDestinationId(Long sourceId, Long destinationId);
}
