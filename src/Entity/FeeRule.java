
package Entity;
import Entity.Enum.Mode;
import Entity.Enum.OperationType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class FeeRule {


    private UUID id;
    private OperationType operationType;
    private BigDecimal value;
    private Currency currency;
    private boolean isActive;
    private Mode mode;


    public FeeRule() {}

    public FeeRule(OperationType operationType, Mode mode, BigDecimal value, Currency currency, boolean isActive) {
        this.operationType = operationType;
        this.mode = mode;
        this.value = value;
        this.currency = currency;
        this.isActive = isActive;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }





    @Override
    public String toString() {
        return "FeeRule{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                ", mode=" + mode +
                ", value=" + value +
                ", currency=" + currency +
                ", isActive=" + isActive +
                '}';
    }
}
