package controller;

import Entity.Account;
import Entity.Client;
import Entity.Credit;
import Entity.Enum.AccountType;
import Entity.Enum.CreditType;
import Service.Interfaces.AccountService;
import Service.Interfaces.CreditService;




import java.util.Scanner;
import java.util.UUID;

public class CreditController {

    private final CreditService creditService;
    private final AccountService accountService;

    public CreditController(CreditService creditService, AccountService accountService) {
        this.creditService = creditService;
        this.accountService = accountService;
    }

    public void demanderCredit(Scanner scanner) {
        System.out.print("CIN du client : ");
        String cin = scanner.nextLine();

        Client client = accountService.getCinClient(cin);
        if (client == null) {
            System.out.println("Client introuvable.");
            return;
        }

        Account creditAccount = client.getAccounts().stream()
                .filter(a -> a.getType() == AccountType.CREDIT)
                .findFirst()
                .orElse(null);

        if (creditAccount == null) {
            System.out.println("Aucun compte de type CREDIT.");
            return;
        }

        System.out.print("Montant du crédit : ");
        double amount = scanner.nextDouble();

        System.out.print("Durée (en mois) : ");
        int duree = scanner.nextInt();

        System.out.print("Taux d’intérêt (%) : ");
        float taux = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Justification : ");
        String justification = scanner.nextLine();

        System.out.print("Type de crédit (SIMPLE / COMPOSEE) : ");
        CreditType type = CreditType.valueOf(scanner.nextLine().toUpperCase());

        Credit credit = creditService.demanderCredit(
                creditAccount.getId(), amount, duree, taux, justification, type
        );

        if (credit != null) {
            System.out.println("Crédit demandé avec succès !");
            System.out.println("Montant : " + credit.getAmount());
            System.out.println("Durée : " + credit.getDuree() + " mois");
            System.out.println("Taux : " + credit.getTaux() + " %");
            System.out.println("Statut : " + credit.getStatus());

            double mensualite;
            if (credit.getCreditType() == CreditType.SIMPLE) {
                double interetTotal = credit.getAmount() * (credit.getTaux() / 100);
                mensualite = (credit.getAmount() + interetTotal) / credit.getDuree();
                System.out.printf("Mensualité estimée : %.2f MAD\n", mensualite);
            }

            System.out.println("Ce crédit doit être validé par un manager.");
        } else {
            System.out.println("Demande de crédit échouée.");
        }
    }
    public void validerCredit(Scanner scanner) {
        System.out.print("ID du crédit à valider : ");
        UUID creditId = UUID.fromString(scanner.nextLine());

        boolean result = creditService.accepterCredit(creditId);
        if (result) {
            System.out.println("Crédit validé et mensualités générées !");
        } else {
            System.out.println("Échec de la validation du crédit.");
        }
    }


}
