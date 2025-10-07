package Repository.Inpelement;

import Entity.Account;
import Entity.Client;
import Entity.Enum.AccountType;
import Repository.Interfaces.ClientRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class inMemoryClientRepository  implements ClientRepository {

    public Client save(Client client) {
        String sql = "INSERT INTO client (firstname, lastname, cin, phonenumber, email) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getCIN());
            stmt.setString(4, client.getPhoneNumber());
            stmt.setString(5, client.getEmail());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                client.setId(id);
                System.out.println("Client enregistré avec succès avec ID = " + id);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return client;
    }



    public Client getByCin(String cin) {
        String sql = """
        SELECT c.id as client_id, c.cin, c.firstname, c.lastname,
               a.id as account_id, a.balance, a.type,a.ownername
        FROM client c
        LEFT JOIN account a ON c.id = a.client_id
        WHERE c.cin = ?
    """;

        Client client = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (client == null) {
                    client = new Client();
                    client.setId(UUID.fromString(rs.getString("client_id")));
                    client.setCIN(rs.getString("cin"));
                    client.setFirstName(rs.getString("firstname"));
                    client.setLastName(rs.getString("lastname"));
                    client.setAccounts(new ArrayList<>());
                }


                UUID accountId = (UUID) rs.getObject("account_id");
                if (accountId != null) {
                    Account account = new Account();
                    account.setId(accountId);
                    account.setBalance(rs.getDouble("balance"));
                    account.setOwnerName(rs.getString("ownername"));
                    String typeStr = rs.getString("type");
                    if (typeStr != null) {
                        account.setType(AccountType.valueOf(typeStr));
                    }

                    client.getAccounts().add(account);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }
    @Override
    public Client getById(UUID id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client client = new Client();
                client.setId((UUID) rs.getObject("id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setCIN(rs.getString("cin"));
                client.setEmail(rs.getString("email"));
                client.setPhoneNumber(rs.getString("phone"));
                client.setSalaire(rs.getDouble("salaire"));
                // Ajouter les comptes si nécessaire
                return client;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}
