package Repository.Inpelement;

import Entity.*;
import Entity.Enum.*;

import Repository.Interfaces.CreditRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class inMemoryCreditRepository implements CreditRepository {

    @Override
    public Credit save(Credit credit) {
        String sql = "INSERT INTO credit (id, amount, duree, taux, justification, credit_type, account_id, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, credit.getId());
            stmt.setDouble(2, credit.getAmount());
            stmt.setInt(3, credit.getDuree());
            stmt.setFloat(4, credit.getTaux());
            stmt.setString(5, credit.getJustification());
            stmt.setString(6, credit.getCreditType().name());
            stmt.setObject(7, credit.getAccount().getId());
            stmt.setString(8, credit.getStatus().name());
            stmt.setTimestamp(9, Timestamp.from(credit.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.from(credit.getUpdatedAt()));

            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return credit;
    }

    @Override
    public Credit getById(UUID id) {
        // Implémentation similaire à save() (chargement par ID)
        return null;
    }

    @Override
    public List<Credit> getAll() {
        // Retourner tous les crédits
        return null;
    }

    @Override
    public List<Credit> getCreditsByAccountId(UUID accountId) {
        // Retourner tous les crédits liés à un compte
        return null;
    }

    @Override
    public boolean hasActiveOrLateCredit(UUID accountId) {
        String sql = "SELECT COUNT(*) FROM credit WHERE account_id = ? AND status IN ('ACTIVE', 'LATE')";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
