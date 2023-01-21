package ru.filit.mdma.repository;

import java.util.List;
import ru.filit.mdma.model.entity.Client;


public interface ClientRepository {

  Client getClientById(String id);

  List<Client> findClientByParams(Client clientSearch);

}
