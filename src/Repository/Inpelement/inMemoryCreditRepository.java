package Repository.Inpelement;

import Entity.Account;
import Entity.Credit;
import Repository.Interfaces.CreditRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class inMemoryCreditRepository implements CreditRepository {

    @Override
    public Credit save(Credit credit) {
        String sql = "INSERT INTO credit (amount, duree, taux, justification, credit_type, account_id, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?::credit_type, ?, ?::credit_status, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, credit.getAmount());
            stmt.setInt(2, credit.getDuree());
            stmt.setFloat(3, credit.getTaux());
            stmt.setString(4, credit.getJustification());
            stmt.setString(5, credit.getCreditType().name().toLowerCase());
            stmt.setObject(6, credit.getAccount().getId());
            stmt.setString(7, credit.getStatus().name().toLowerCase());
            stmt.setTimestamp(8, Timestamp.from(credit.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.from(credit.getUpdatedAt()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                credit.setId((UUID) rs.getObject("id"));
            }

            return credit;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du crédit", e);
        }
    }



    @Override
    public Credit getById(UUID id) {
        String sql = "SELECT * FROM credit WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCredit(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Credit> getAll() {
        List<Credit> credits = new ArrayList<>();
        String sql = "SELECT * FROM credit";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                credits.add(mapRowToCredit(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return credits;
    }


    @Override
    public boolean hasActiveOrLateCredit(UUID accountId) {
        String sql = "SELECT COUNT(*) FROM credit WHERE account_id = ? AND status IN ('active', 'late')";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Credit mapRowToCredit(ResultSet rs) throws SQLException {
        Credit credit = new Credit();
        credit.setId((UUID) rs.getObject("id"));
        credit.setAmount(rs.getDouble("amount"));
        credit.setDuree(rs.getInt("duree"));
        credit.setTaux(rs.getFloat("taux"));
        credit.setJustification(rs.getString("justification"));

        // Sécurise l'enum
        try {
            credit.setCreditType(Enum.valueOf(Entity.Enum.CreditType.class, rs.getString("credit_type").toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Type de crédit invalide : " + rs.getString("credit_type"));
        }

        // Account ID
        UUID accountId = (UUID) rs.getObject("account_id");
        if (accountId != null) {
            Account account = new Account();
            account.setId(accountId);
            credit.setAccount(account);
        } else {
            credit.setAccount(null);
        }


        try {
            credit.setStatus(Enum.valueOf(Entity.Enum.CreditStatus.class, rs.getString("status").toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Statut de crédit invalide : " + rs.getString("status"));
        }

        credit.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        credit.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());

        return credit;
    }

}

