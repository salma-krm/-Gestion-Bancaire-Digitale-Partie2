package Repository.Inpelement;

import Entity.Account;
import Entity.Enum.TransactionType;
import Entity.Transaction;
import Repository.Interfaces.TransactionRepository;
import Repository.Interfaces.AccountRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.util.UUID;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final AccountRepository accountRepository;

    public InMemoryTransactionRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction create(TransactionType type, double amount, UUID accountId) {
        String sql = "INSERT INTO transactions (account_id, type, amount, created_at) VALUES (?, ?::transaction_type, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, accountId);
            stmt.setString(2, type.name());
            stmt.setDouble(3, amount);
            stmt.setTimestamp(4, Timestamp.from(java.time.Instant.now()));

            stmt.executeUpdate();

            // Charger le compte après transaction
            Account account = accountRepository.getById(accountId);

            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setCreatedAt(java.time.Instant.now());

            return transaction;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
