
package Service.Inpement;

import Entity.Account;
import Entity.Client;
import Repository.Interfaces.AccountRepository;
import Repository.Interfaces.ClientRepository;
import Service.Interfaces.AccountService;

import java.util.UUID;

public class AccountServiceImplement implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountServiceImplement(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client, Account account) {
        if (clientRepository.getByCin(client.getCIN()) != null) {
            System.out.println("Client déjà existant");
            return null;
        }
        Client savedClient = clientRepository.save(client);
        accountRepository.save(account, savedClient);
        return savedClient;
    }

    @Override
    public Client getCinClient(String cin) {
        if (cin == null || cin.isEmpty()) {
            return null;
        }
        return clientRepository.getByCin(cin);
    }

    @Override
    public Account Update(UUID accountId, Account amount) {
        if (accountId == null || amount == null || amount.getBalance() <= 0) {
            System.out.println("Paramètres invalides pour update");
            return null;
        }

        Account currentAccount = accountRepository.getById(accountId);
        if (currentAccount == null) {
            System.out.println("Compte non trouvé pour l'ID: " + accountId);
            return null;
        }

        double newBalance = currentAccount.getBalance() + amount.getBalance();
        currentAccount.setBalance(newBalance);

        return accountRepository.update(accountId, currentAccount);
    }

    @Override
    public Account getById(UUID id) {
        if (id == null) {
            return null;
        }
        return accountRepository.getById(id);
    }

    @Override
    public Account getByidClient(UUID clientId) {
        if (clientId == null) {
            return null;
        }
        return accountRepository.getByidClient(clientId);
    }
}
