package Service.Interfaces;

import Entity.Credit;
import Entity.Enum.CreditType;

import java.util.List;
import java.util.UUID;

public interface CreditService {
    Credit demanderCredit(UUID accountId, Double amount, int duree, float taux, String justification, CreditType creditType);
    List<Credit> getCreditsByAccount(UUID accountId);
}
