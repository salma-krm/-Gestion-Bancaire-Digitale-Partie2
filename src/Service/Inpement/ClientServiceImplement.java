package Service.Inpement;

import Entity.Account;
import Entity.Client;
import Repository.Interfaces.AccountRepository;
import Repository.Interfaces.ClientRepository;
import Service.Interfaces.ClientService;

public class ClientServiceImplement implements ClientService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository ;

    public ClientServiceImplement(ClientRepository clientRepository,AccountRepository accountRepository){
         this.clientRepository =clientRepository;
         this.accountRepository = accountRepository;
    }

    public Client save(Client client, Account account) {

        if (clientRepository.getByCin(client.getCIN()) != null) {
            System.out.println("Client déjà existant");
            return null;
        }
        Client savedClient = clientRepository.save(client);

        accountRepository.save(account, savedClient);

        return savedClient;
    }

//      public Client getAll(){
//
//    }
//    public  UUID getById(){
//    }
//     public String getByEmail(){
//
//    }
}
