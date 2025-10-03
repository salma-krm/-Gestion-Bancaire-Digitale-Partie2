package Entity;

import Entity.Enum.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String createdAt;
    private List<Account> accounts = new ArrayList<>();
    private Role role;

    public User() {}

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }



    public UUID getId() { return id; }
    public String getcreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt){this.createdAt = createdAt;};
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Account> getAccounts() { return accounts; }
    public void addAccount(Account account) { this.accounts.add(account); }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
