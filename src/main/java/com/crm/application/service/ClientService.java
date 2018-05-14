package com.crm.application.service;

import com.crm.application.utilModels.ClientsPerMonth;
import com.crm.application.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Iterable<Client> listAllClient();

    Client getClientById(long id);

    Optional<Client> getClientByPesel(String pesel);

    Optional<Client> getClientByNip(String nip);

    Optional<Client> getClientByRegon(String regon);

    void saveClient(Client client);

    Client updateClient(long id, Client client);

    void deleteClient(long id);

    Iterable<Client> findAllClientFilter(String client_type, String email, String phone, String nip, String pesel, String name);

    void updateStatus(Long id, String status);

    List<Client> getClientsWithGroupFilter(String listOfId);

    List<ClientsPerMonth> clientsPerMonthStatistics(Integer year);

    List<ClientsPerMonth> clientTypePercentage();

    List<List<Client>> clientsPerStatusTypes();

    List<Long> getClientsByGroupId(Long id);
}
