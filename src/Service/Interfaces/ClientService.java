package Service.Interfaces;

import Entity.Account;
import Entity.Client;
import Entity.Enum.AccountType;

import java.util.UUID;

public interface ClientService {
    Client save(Client client, Account account);
//    Client getAll();
//     UUID getById();
//     String getByEmail ();

}
