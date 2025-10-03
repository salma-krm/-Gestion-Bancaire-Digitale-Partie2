package Repository.Interfaces;

import Entity.Account;
import Entity.Client;

import java.util.UUID;

public interface AccountRepository {
    Client save(Account account,Client Client);
    Account update (UUID accountId, Account account);
    Account getById(UUID id);
     Account getByidClient (UUID id);
//    String getByString(String email );

}
