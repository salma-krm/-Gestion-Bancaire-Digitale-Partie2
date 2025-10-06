package Entity;

import Entity.Enum.TransactionType;
import Entity.Enum.VirementStatus;

import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private Double amount;
    private Account transferIn;
    private Account transferOut;
    private TransactionType type;
    private VirementStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private FeeRule feeRule;
    private Account account;



    public Transaction() {
    }

    public Transaction(UUID id, Double amount, Account transferIn, Account transferOut,
                       TransactionType type, VirementStatus status,
                       Instant createdAt, Instant updatedAt, FeeRule feeRule, Account account) {
        this.id = id;
        this.amount = amount;
        this.transferIn = transferIn;
        this.transferOut = transferOut;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.feeRule = feeRule;
        this.account = account;
    }

    // --- Getters & Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(Account transferIn) {
        this.transferIn = transferIn;
    }

    public Account getTransferOut() {
        return transferOut;
    }

    public void setTransferOut(Account transferOut) {
        this.transferOut = transferOut;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public VirementStatus getStatus() {
        return status;
    }

    public void setStatus(VirementStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FeeRule getFeeRule() {
        return feeRule;
    }

    public void setFeeRule(FeeRule feeRule) {
        this.feeRule = feeRule;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transferIn=" + transferIn +
                ", transferOut=" + transferOut +
                ", type=" + type +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", feeRule=" + feeRule +
                ", account=" + (account != null ? account.getId() : null) +
                '}';
    }
}
