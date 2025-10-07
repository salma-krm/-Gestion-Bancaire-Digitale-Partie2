package Repository.Interfaces;

import Entity.Client;

import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);
    Client getByCin(String cin);
    Client getById(UUID id);
//    String getByEmail(String email);
//    Client getAll();
}
