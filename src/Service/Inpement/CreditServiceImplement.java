package Service.Inpement;
import Service.util.MensualiteRunnable;
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

        if (account.getType() != AccountType.CREDIT) {
            System.out.println("Ce n’est pas un compte de type CREDIT.");
            return null;
        }

        boolean hasActiveOrLate = creditRepository.hasActiveOrLateCredit(accountId);
        if (hasActiveOrLate) {
            System.out.println("Le client a déjà un crédit actif ou en retard.");
            return null;
        }

        Client client =   account.getClient();
        if (client == null || client.getSalaire() <= 0) {
            System.out.println("Salaire non défini ou invalide. ");
            return null;
        }

        double mensualite;
        if (creditType == CreditType.SIMPLE) {
            double interetTotal = amount * (taux / 100);
            mensualite = (amount + interetTotal) / duree;
        }  else {
            System.out.println("Type de crédit invalide.");
            return null;
        }

        double plafondMensualite = client.getSalaire() * 0.4;
        if (mensualite > plafondMensualite) {
            System.out.printf("Mensualité %.2f dépasse 40%% du salaire %.2f\n", mensualite, plafondMensualite);
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
    public boolean accepterCredit(UUID creditId) {
        Credit credit = creditRepository.getById(creditId);

        if (credit == null || credit.getStatus() != CreditStatus.PENDING) {
            System.out.println("Crédit introuvable ou déjà traité.");
            return false;
        }

        credit.setStatus(CreditStatus.ACTIVE);
        credit.setUpdatedAt(Instant.now());

        creditRepository.save(credit);

        MensualiteRunnable task = new MensualiteRunnable(credit);
        new Thread(task).start();

        System.out.println("Crédit activé avec succès.");
        return true;
    }



}
