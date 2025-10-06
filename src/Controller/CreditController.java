package Controller;

import Entity.Credit;
import Entity.Enum.CreditType;
import Service.Interfaces.CreditService;

import java.util.List;
import java.util.UUID;

public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    public Credit demanderCredit(UUID accountId, Double amount, int duree, float taux, String justification, CreditType creditType) {
        return creditService.demanderCredit(accountId, amount, duree, taux, justification, creditType);
    }

    public List<Credit> getCreditsByAccount(UUID accountId) {
        return creditService.getCreditsByAccount(accountId);
    }
}
