package Repository.Interfaces;

import Entity.Credit;

import java.util.List;
import java.util.UUID;

public interface CreditRepository {
    Credit save(Credit credit);
    Credit getById(UUID id);
    List<Credit> getAll();
    List<Credit> getCreditsByAccountId(UUID accountId);
    boolean hasActiveOrLateCredit(UUID accountId);
}
