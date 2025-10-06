package Controller;

import Entity.Transaction;
import Entity.Enum.TransactionType;
import Entity.User;
import Service.Interfaces.TransactionService;

import java.util.UUID;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction deposit(double amount, UUID accountId) {
        if (amount <= 0 || accountId == null) return null;
        return transactionService.create(TransactionType.DEPOSIT, accountId, amount);
    }

    public Transaction withdraw(double amount, UUID accountId) {
        if (amount <= 0 || accountId == null) return null;
        return transactionService.create(TransactionType.WITHDRAW, accountId, amount);
    }

    public Transaction internalTransfer(UUID sourceId, UUID destId, double amount) {
        if (sourceId == null || destId == null || amount <= 0) return null;
        return transactionService.internalTransfer(sourceId, destId, amount);
    }

    public Transaction externalTransfer(UUID sourceId, String externalAccount, double amount) {
        if (sourceId == null || externalAccount == null || amount <= 0) return null;
        return transactionService.externalTransfer(sourceId, externalAccount, amount);
    }


}
