package Controller;

import Entity.Transaction;
import Service.Interfaces.TransactionService;

import java.util.UUID;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction deposit(double amount, UUID accountId) {
        return transactionService.create(Entity.Enum.TransactionType.DEPOSIT, accountId, amount);
    }

    public Transaction withdraw(double amount, UUID accountId) {
        return transactionService.create(Entity.Enum.TransactionType.WITHDRAW, accountId, amount);
    }

    public Transaction internalTransfer(UUID sourceAccountId, UUID destAccountId, double amount) {
        return ((Service.Inpement.TransactionServiceImplement) transactionService).internalTransfer(sourceAccountId, destAccountId, amount);
    }

    public Transaction externalTransfer(UUID sourceAccountId, String externalAccountNumber, double amount) {
        return ((Service.Inpement.TransactionServiceImplement) transactionService).externalTransfer(sourceAccountId, externalAccountNumber, amount);
    }
}
