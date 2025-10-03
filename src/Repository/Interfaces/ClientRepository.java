package Repository.Interfaces;

import Entity.Client;

public interface ClientRepository {
    Client save(Client client);
    Client getByCin(String cin);
//    String getByEmail(String email);
//    Client getAll();
}
