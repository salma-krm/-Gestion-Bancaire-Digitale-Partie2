package Service.Interfaces;

import Entity.Account;
import Entity.Enum.TransactionType;
import Entity.Transaction;
import Entity.User;

import java.util.UUID;

public interface TransactionService {
     Transaction create(TransactionType type, UUID accountId, double amount);
      Transaction internalTransfer(UUID sourceAccountId, UUID destAccountId, double amount);
     Transaction externalTransfer(UUID sourceAccountId, String externalAccountNumber, double amount);

}
