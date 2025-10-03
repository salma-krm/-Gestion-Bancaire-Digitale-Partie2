package Repository.Interfaces;

import Entity.Enum.TransactionType;
import Entity.Transaction;
import java.util.UUID;

public interface TransactionRepository {
    Transaction create(TransactionType type, double amount, UUID accountId);
}
