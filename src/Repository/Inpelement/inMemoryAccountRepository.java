// ===================== inMemoryAccountRepository =====================
package Repository.Inpelement;

import Entity.Account;
import Entity.Client;
import Entity.Enum.AccountType;
import Repository.Interfaces.AccountRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class inMemoryAccountRepository implements AccountRepository {

    @Override
    public Client save(Account account, Client client) {
        String sql = "INSERT INTO account (client_id, balance, type) " +
                "VALUES (?, ?, ?::account_type) RETURNING id";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, client.getId());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getType().name());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID accountId = (UUID) rs.getObject("id");
                account.setId(accountId);
                System.out.println("Compte enregistré avec succès avec ID = " + accountId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Account update(UUID accountId, Account newAccountData) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newAccountData.getBalance());
            stmt.setObject(2, accountId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return getById(accountId);
            } else {
                System.out.println("Aucun compte trouvé avec l'ID = " + accountId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getById(UUID id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        Account account = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(UUID.fromString(rs.getString("id")));
                account.setBalance(rs.getDouble("balance"));
                String typeStr = rs.getString("type");
                if (typeStr != null) {
                    account.setType(AccountType.valueOf(typeStr));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    @Override
    public Account getByidClient(UUID clientId) {
        String sql = "SELECT * FROM account WHERE client_id = ?";
        Account account = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, clientId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                account = new Account();
                account.setId(UUID.fromString(result.getString("id")));
                account.setBalance(result.getDouble("balance"));
                String typeStr = result.getString("type");
                if (typeStr != null) {
                    account.setType(AccountType.valueOf(typeStr));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }
}
