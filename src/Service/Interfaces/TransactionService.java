package Service.Interfaces;

import Entity.Account;
import Entity.Enum.TransactionType;
import Entity.Transaction;

import java.util.UUID;

public interface TransactionService {
     Transaction create(TransactionType type, UUID accountId, double amount);
     Transaction transfer(UUID sourceAccountId, UUID destAccountId, double amount);
}
