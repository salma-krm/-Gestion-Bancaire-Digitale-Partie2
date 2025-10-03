// ===================== AccountController =====================
package Controller;

import Entity.Account;
import Entity.Client;
import Service.Interfaces.AccountService;
import utils.validatedAccount;

import java.util.UUID;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Client create(Client client, Account account) {
        if (client == null || account == null) {
            System.out.println("Client ou compte manquant.");
            return null;
        }
        if (!validatedAccount.validateAccount(account)) {
            System.out.println("Compte invalide.");
            return null;
        }
        return accountService.save(client, account);
    }

    public Client getCinClient(String cin) {
        if (cin == null || cin.isEmpty()) {
            return null;
        }
        return accountService.getCinClient(cin);
    }



    public Account getById(UUID id) {
        if (id == null) return null;
        return accountService.getById(id);
    }

    public Account getAccountByClientId(UUID clientId) {
        if (clientId == null) return null;
        return accountService.getByidClient(clientId);
    }
}
