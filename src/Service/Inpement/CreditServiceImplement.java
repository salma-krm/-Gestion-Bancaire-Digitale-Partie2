package Service.Inpement;

import Entity.*;
import Entity.Enum.*;
import Repository.Interfaces.*;
import Service.Interfaces.CreditService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CreditServiceImplement implements CreditService {

    private final CreditRepository creditRepository;
    private final AccountRepository accountRepository;

    public CreditServiceImplement(CreditRepository creditRepository, AccountRepository accountRepository) {
        this.creditRepository = creditRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Credit demanderCredit(UUID accountId, Double amount, int duree, float taux, String justification, CreditType creditType) {
        Account account = accountRepository.getById(accountId);

        if (account == null) {
            System.out.println("Compte introuvable.");
            return null;
        }

        boolean hasActiveOrLate = creditRepository.hasActiveOrLateCredit(accountId);
        if (hasActiveOrLate) {
            System.out.println("Vous avez déjà un crédit actif ou en retard.");
            return null;
        }

        Credit credit = new Credit();
        credit.setId(UUID.randomUUID());
        credit.setAmount(amount);
        credit.setDuree(duree);
        credit.setTaux(taux);
        credit.setJustification(justification);
        credit.setCreditType(creditType);
        credit.setAccount(account);
        credit.setStatus(CreditStatus.PENDING);
        credit.setCreatedAt(Instant.now());
        credit.setUpdatedAt(Instant.now());

        return creditRepository.save(credit);
    }

    @Override
    public List<Credit> getCreditsByAccount(UUID accountId) {
        return creditRepository.getCreditsByAccountId(accountId);
    }
}
