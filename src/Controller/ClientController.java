package Controller;

import Entity.Account;
import Entity.Client;
import Entity.Enum.AccountType;
import Service.Inpement.ClientServiceImplement;
import Service.Interfaces.ClientService;
import utils.validatedClient;

public class ClientController {
    private final ClientService clientService ;

    public ClientController(ClientService clientService){
        this.clientService  =  clientService ;

    }
    public Client create(Client client, Account account){
        if(client!=null && validatedClient.validatedClient(client)  ){
            clientService.save(client,account);
        }else{
            return null;
        }
        return client;
    }
}
