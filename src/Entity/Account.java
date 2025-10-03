package Entity;

import Entity.Enum.AccountType;

import java.time.Instant;
import java.util.UUID;

public class Account {
    private UUID id;
    private Client client;
    private String accountId;
    private String ownerName;
    private Boolean isActive;
    private Double balance;
    private Instant createdAt;
    private Instant updatedAt;
    private AccountType type;

    public Account() {
        this.isActive = true;
    }

    public Account(Client client, double balance, AccountType type, Boolean isActive) {
        this.client = client;
        this.balance = balance;
        this.type = type;
        this.accountId = generateAccountId();
        this.isActive = (isActive != null) ? isActive : true;
    }

    public Account(double balance) {
        this.balance = balance;
        this.isActive = true;
    }

    public Account(AccountType type) {
        this.type = type;
        this.isActive = true;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public boolean getActive() { return isActive != null && isActive; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public String getAccountId() { return accountId; }

    private String generateAccountId() {
        int random1 = (int)(Math.random() * 10000);
        int random2 = (int)(Math.random() * 10000);
        String uuidPart = UUID.randomUUID().toString().substring(0, 4);
        return "BK-" + random1 + "-" + random2 + "-" + uuidPart;
    }

    public void setActive(boolean active) { this.isActive = active; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", isActive=" + isActive +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", type=" + type +
                '}';
    }
}
