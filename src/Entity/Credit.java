package Entity;

import Entity.Enum.CreditStatus;
import Entity.Enum.CreditType;

import java.time.Instant;
import java.util.UUID;

public class Credit {
    private UUID id;
    private Double amount;
    private int duree;
    private float taux;
    private FeeRule feeRule;
    private String justification;
    private CreditType creditType;
    private Account account;
    private CreditStatus status;
    private Instant createdAt;
    private Instant updatedAt;






    public Credit() {
    }
    public Credit(UUID id, Double amount, int duree, float taux, FeeRule feeRule,
                  String justification, CreditType creditType, Account account,
                  CreditStatus status, Instant createdAt, Instant updatedAt,double salaire) {
        this.id = id;
        this.amount = amount;
        this.duree = duree;
        this.taux = taux;
        this.feeRule = feeRule;
        this.justification = justification;
        this.creditType = creditType;
        this.account = account;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }


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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public float getTaux() {
        return taux;
    }

    public void setTaux(float taux) {
        this.taux = taux;
    }

    public FeeRule getFeeRule() {
        return feeRule;
    }

    public void setFeeRule(FeeRule feeRule) {
        this.feeRule = feeRule;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CreditStatus getStatus() {
        return status;
    }

    public void setStatus(CreditStatus status) {
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


    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", amount=" + amount +
                ", duree=" + duree +
                ", taux=" + taux +
                ", feeRule=" + feeRule +
                ", justification='" + justification + '\'' +
                ", creditType=" + creditType +
                ", account=" + account +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
