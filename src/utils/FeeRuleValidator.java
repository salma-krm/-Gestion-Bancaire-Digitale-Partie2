package utils;

import Entity.FeeRule;
import java.math.BigDecimal;
import java.util.Currency;

public class FeeRuleValidator {

    public static void validate(FeeRule feeRule) {
        if (feeRule.getOperationType() == null) {
            throw new IllegalArgumentException("Le type d'opération est requis.");
        }

        if (feeRule.getMode() == null) {
            throw new IllegalArgumentException("Le mode est requis.");
        }

        if (feeRule.getValue() == null || feeRule.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La valeur doit être positive.");
        }

        if (feeRule.getCurrency() == null) {
            throw new IllegalArgumentException("La devise est requise.");
        }
    }
}
