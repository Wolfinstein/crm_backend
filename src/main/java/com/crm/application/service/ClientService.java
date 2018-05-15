package com.crm.application.service;

import com.crm.application.model.Client;
import com.crm.application.utilModels.ClientsPerMonth;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();

    Optional<Client> getClientById(Long id);

    Optional<Client> getClientByPesel(String pesel);

    Optional<Client> getClientByNip(String nip);

    Optional<Client> getClientByRegon(String regon);

    void saveClient(Client client);

    void updateClient(Long id, Client client);

    void deleteClient(Long id);

    List<Client> getAllClientsWithParams(String client_type, String email, String phone, String nip, String pesel, String name);

    void updateClientStatus(Long id, String status);

    List<Client> getClientsWithGroupFilter(String listOfId);

    List<ClientsPerMonth> getClientsPerMonthStatistics(Integer year);

    List<ClientsPerMonth> getClientTypePercentage();

    List<List<Client>> getClientsPerStatusTypes();

    List<Long> getClientsByGroupId(Long id);
}
