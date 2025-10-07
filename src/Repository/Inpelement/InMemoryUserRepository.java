package Repository.Inpelement;

import Entity.Enum.Role;
import Entity.User;
import Repository.Interfaces.UserRepository;
import com.systemeBancaire.com.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {

    @Override
    public User save(User user) {
        String sql = "INSERT INTO \"user\" (firstname, lastname, email, password, role) VALUES (?, ?, ?, ?, ?::role)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole().name());

            int inserted = stmt.executeUpdate();
            if (inserted > 0) {
                System.out.println("Utilisateur inséré avec succès !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setFirstName(rs.getString("lastname"));
                user.setLastName(rs.getString("firstname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM \"user\"";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
