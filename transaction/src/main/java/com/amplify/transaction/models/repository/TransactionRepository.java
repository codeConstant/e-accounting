package com.amplify.transaction.models.repository;

import com.amplify.transaction.models.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
