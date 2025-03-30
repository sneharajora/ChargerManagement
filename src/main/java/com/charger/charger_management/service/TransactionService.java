package com.charger.charger_management.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public void startTransaction(String chargerId, String transactionId) {
        System.out.println("Starting transaction for charger: " + chargerId + " with ID: " + transactionId);
    }

    public void stopTransaction(String transactionId) {
        System.out.println("Stopping transaction with ID: " + transactionId);
    }
}
