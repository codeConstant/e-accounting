package com.amplify.transaction.models.service;

import com.amplify.transaction.models.entity.Transaction;
import com.amplify.transaction.models.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> saveAll(List<Transaction> transactions){
        return transactionRepository.saveAll(transactions);
    }

}
