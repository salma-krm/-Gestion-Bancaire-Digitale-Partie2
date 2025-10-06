package Repository.Inpelement;

import Entity.Account;
import Entity.Enum.TransactionType;
import Entity.Enum.VirementStatus;
import Entity.Transaction;
import Repository.Interfaces.TransactionRepository;
import Repository.Interfaces.AccountRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.UUID;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final AccountRepository accountRepository;

    public InMemoryTransactionRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public Transaction create(TransactionType type, double amount, UUID accountId) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            String sql = "INSERT INTO transactions (id, account_id, type, amount, status, created_at) " +
                    "VALUES (?, ?, ?::transaction_type, ?, ?::virement_status, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            UUID transactionId = UUID.randomUUID();

            stmt.setObject(1, transactionId);
            stmt.setObject(2, accountId);
            stmt.setString(3, type.name());
            stmt.setDouble(4, amount);
            stmt.setString(5, VirementStatus.PENDING.name());
            stmt.setTimestamp(6, Timestamp.from(Instant.now()));

            stmt.executeUpdate();

            Account account = accountRepository.getById(accountId);

            Transaction transaction = new Transaction();
            transaction.setId(transactionId);
            transaction.setAccount(account);
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setStatus(VirementStatus.PENDING);
            transaction.setCreatedAt(Instant.now());

            return transaction;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Transaction transaction) {

    }


}
