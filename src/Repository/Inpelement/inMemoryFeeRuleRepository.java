package Repository.Inpelement;

import Entity.Enum.Mode;
import Entity.Enum.OperationType;
import Entity.FeeRule;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class inMemoryFeeRuleRepository implements Repository.Interfaces.FeeRuleRepository {

    @Override

    public FeeRule create(FeeRule feeRule) {
        String sql = "INSERT INTO fee_rule (operation_type, mode, value, currency, is_active) " +
                "VALUES (?::operation_type, ?::mode_enum, ?, ?, ?) RETURNING id";


        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feeRule.getOperationType().name());
            stmt.setString(2, feeRule.getMode().name());
            stmt.setBigDecimal(3, feeRule.getValue());
            stmt.setString(4, feeRule.getCurrency().getCurrencyCode());
            stmt.setBoolean(5, feeRule.isActive());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                feeRule.setId(id);
                System.out.println(" FeeRule créée avec succès avec ID = " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return feeRule;
    }


    @Override
    public FeeRule update(UUID uuid, FeeRule feeRule) {
        String sql = "UPDATE fee_rule SET value = ?, is_active = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, feeRule.getValue());
            stmt.setBoolean(2, feeRule.isActive());
            stmt.setObject(3, uuid);

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                System.out.println(" Aucun FeeRule mis à jour.");
                return null;
            }

            System.out.println("FeeRule mis à jour avec succès.");
            return feeRule;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FeeRule getById(UUID uuid) {
        String sql = "SELECT * FROM fee_rule WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, uuid);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FeeRule feeRule = new FeeRule();
                feeRule.setId(UUID.fromString(rs.getString("id")));
                feeRule.setOperationType(OperationType.valueOf(rs.getString("operation_type")));
                feeRule.setMode(Mode.valueOf(rs.getString("mode")));
                feeRule.setValue(rs.getBigDecimal("value"));
                feeRule.setCurrency(Currency.getInstance(rs.getString("currency")));
                feeRule.setActive(rs.getBoolean("is_active"));
                return feeRule;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<FeeRule> getAll() {
        List<FeeRule> feeRules = new ArrayList<>();
        String sql = "SELECT * FROM fee_rule";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FeeRule feeRule = new FeeRule();
                UUID id = (UUID) rs.getObject("id");
                feeRule.setId(UUID.fromString(rs.getString("id")));
                feeRule.setOperationType(OperationType.valueOf(rs.getString("operation_type")));
                feeRule.setMode(Mode.valueOf(rs.getString("mode")));
                feeRule.setValue(rs.getBigDecimal("value"));
                feeRule.setCurrency(Currency.getInstance(rs.getString("currency")));
                feeRule.setActive(rs.getBoolean("is_active"));

                feeRules.add(feeRule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return feeRules;
    }

    @Override
    public FeeRule findByOperationTypeAndModeAndCurrency(OperationType operationType, Mode mode, Currency currency) {
        String sql = "SELECT * FROM fee_rule WHERE operation_type = ?::operation_type AND mode = ?::mode_enum AND currency = ? AND is_active = true LIMIT 1";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, operationType.name());
            stmt.setString(2, mode.name());
            stmt.setString(3, currency.getCurrencyCode());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FeeRule feeRule = new FeeRule();
                feeRule.setId(UUID.fromString(rs.getString("id")));
                feeRule.setOperationType(operationType);
                feeRule.setMode(mode);
                feeRule.setValue(rs.getBigDecimal("value"));
                feeRule.setCurrency(currency);
                feeRule.setActive(rs.getBoolean("is_active"));
                return feeRule;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
