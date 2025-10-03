package Service.Interfaces;

import Entity.Account;
import Entity.Client;

import java.util.UUID;

public interface AccountService {
    Client save (Client client, Account account );
    Client getCinClient(String cin );
    Account Update  (UUID accountId, Account amount);
    Account getByidClient(UUID id);
    Account getById(UUID id);

}
