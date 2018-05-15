package com.crm.application.service.serviceImpl;

import com.crm.application.model.Client;
import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.GroupRepository;
import com.crm.application.service.ClientService;
import com.crm.application.utilModels.ClientStatusTypes;
import com.crm.application.utilModels.ClientsPerMonth;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final GroupRepository groupRepository;

    @PersistenceContext
    private EntityManager em;
    @PersistenceContext
    private EntityManager entityManager;


    public ClientServiceImpl(ClientRepository clientRepository, GroupRepository groupRepository) {
        this.clientRepository = clientRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return Optional.ofNullable(clientRepository.findOne(id));
    }

    @Override
    public Optional<Client> getClientByPesel(String pesel) {
        return Optional.ofNullable(clientRepository.findOneByPesel(pesel));
    }

    @Override
    public Optional<Client> getClientByNip(String nip) {
        return Optional.ofNullable(clientRepository.findOneByNip(nip));
    }

    @Override
    public Optional<Client> getClientByRegon(String regon) {
        return Optional.ofNullable(clientRepository.findOneByRegon(regon));
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Long id, Client client) {
        Client temp = clientRepository.findOne(id);
        temp.setClient_type(client.getClient_type());
        temp.setEmail(client.getEmail());
        temp.setPhone(client.getPhone());
        temp.setDescription(client.getDescription());
        temp.setCreateTimeStamp();
        temp.setFirstName(client.getFirstName());
        temp.setLastName(client.getLastName());
        temp.setPesel(client.getPesel());
        temp.setName(client.getName());
        temp.setNip(client.getNip());
        temp.setRegon(client.getRegon());
        temp.setTrade(client.getTrade());
        temp.setWebsite(client.getWebsite());
        temp.setTrade(client.getTrade());
        clientRepository.save(temp);
    }

    @Override
    public void deleteClient(Long id) {
        try {
            clientRepository.delete(id);
            clientRepository.flush();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Użtkownik" + getClientById(id) + " nie istnieje");
        }
    }

    @Override
    public List<Client> getAllClientsWithParams(String client_type, String email, String phone, String nip, String pesel, String name) {
        String jpql = "SELECT c FROM Client c";
        String client_typeQuery = "";
        String emailQuery = "";
        String phoneQuery = "";
        String nipQuery = "";
        String peselQuery = "";
        String nameQuery = "";

        if (client_type != null && !client_type.equals("") && !client_type.equals("All Clients") && !client_type.equals("Wszyscy")) {
            client_typeQuery = " WHERE c.client_type LIKE '%" + client_type + "%'";
            if (email != null && !email.equals("")) {
                emailQuery = " AND c.email LIKE '%" + email + "%'";
            }
            if (phone != null && !phone.equals("")) {
                phoneQuery = " AND c.phone LIKE '%" + phone + "%'";
            }
            if (nip != null && !nip.equals("")) {
                nipQuery = " AND c.nip LIKE '%" + nip + "%'";
            }
            if (pesel != null && !pesel.equals("")) {
                peselQuery = " AND c.pesel LIKE '%" + pesel + "%'";
            }
            if (name != null && !name.equals("")) {
                nameQuery = " AND c.name LIKE '%" + name + "%'";
            }
        } else {
            client_typeQuery = " WHERE (c.client_type LIKE 'Individual' OR c.client_type LIKE 'Company')";
            if (email != null && !email.equals("")) {
                emailQuery = " AND c.email LIKE '%" + email + "%'";
            }
            if (phone != null && !phone.equals("")) {
                phoneQuery = " AND c.phone LIKE '%" + phone + "%'";
            }
            if (nip != null && !nip.equals("")) {
                nipQuery = " AND c.nip LIKE '%" + nip + "%'";
            }
            if (pesel != null && !pesel.equals("")) {
                peselQuery = " AND c.pesel LIKE '%" + pesel + "%'";
            }
            if (name != null && !name.equals("")) {
                nameQuery = " AND c.name LIKE '%" + name + "%'";
            }
        }
        return entityManager.createQuery((jpql + client_typeQuery + emailQuery + phoneQuery + nipQuery + peselQuery + nameQuery), Client.class)
                .getResultList();

    }

    @Override
    public void updateClientStatus(Long id, String status) {
        Client client = clientRepository.findOne(id);
        String statusString = status.substring(1, status.length() - 1);

        switch (statusString) {
            case "NewLead": {
                client.setStatus(ClientStatusTypes.NewLead);
                break;
            }
            case "ContactMade": {
                client.setStatus(ClientStatusTypes.ContactMade);
                break;
            }
            case "Qualified": {
                client.setStatus(ClientStatusTypes.Qualified);
                break;
            }
            case "InNegotiation": {
                client.setStatus(ClientStatusTypes.InNegotiation);
                break;
            }
            case "Closed": {
                client.setStatus(ClientStatusTypes.Closed);
                break;
            }
        }
        clientRepository.save(client);
    }

    @Override
    public List<Client> getClientsWithGroupFilter(String listOfId) {

        List<Client> allClients = new ArrayList<>(clientRepository.findAll());

        if (!listOfId.equals("")) {

            List<Long> listOfIdToLong = Stream.of(listOfId.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            for (Long id : listOfIdToLong) {
                allClients = allClients.stream().filter(c -> c.getGroups().contains(groupRepository.findOne(id))).collect(Collectors.toList());
            }

        }
        // after filtering
        return allClients;
    }


    @Override
    public List<ClientsPerMonth> getClientsPerMonthStatistics(Integer year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int newYearValue = 0;
        List<Client> addedInBetweenGivenYear = new ArrayList<>();
        try {
            int yearplus = year + 1;
            java.util.Date minDate = sdf.parse(year + "/01/01 00:00:00");
            java.util.Date maxDate = sdf.parse(yearplus + "/01/01 00:00:00");

            newYearValue = clientRepository.findAll().stream()
                    .filter(p -> p.getCreateTimeStamp().before(minDate)).collect(Collectors.toList()).size();
            addedInBetweenGivenYear = clientRepository.findAll().stream()
                    .filter(p -> p.getCreateTimeStamp().after(minDate)).collect(Collectors.toList());

            addedInBetweenGivenYear = addedInBetweenGivenYear.stream().filter(p -> p.getCreateTimeStamp().before(maxDate)).collect(Collectors.toList());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ClientsPerMonth> outList = new ArrayList<>();
        outList.add(new ClientsPerMonth("January", newYearValue));
        outList.add(new ClientsPerMonth("February", 0));
        outList.add(new ClientsPerMonth("March", 0));
        outList.add(new ClientsPerMonth("April", 0));
        outList.add(new ClientsPerMonth("May", 0));
        outList.add(new ClientsPerMonth("June", 0));
        outList.add(new ClientsPerMonth("July", 0));
        outList.add(new ClientsPerMonth("August", 0));
        outList.add(new ClientsPerMonth("September", 0));
        outList.add(new ClientsPerMonth("October", 0));
        outList.add(new ClientsPerMonth("November", 0));
        outList.add(new ClientsPerMonth("December", 0));

        Calendar cal = Calendar.getInstance();

        for (Client client : addedInBetweenGivenYear) {
            cal.setTime(client.getCreateTimeStamp());
            int month = cal.get(Calendar.MONTH);
            ClientsPerMonth tmp = outList.get(month);
            tmp.setValue(tmp.getValue() + 1);
            outList.set(month, tmp);
        }
        int sum = 0;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);


        for (ClientsPerMonth client : outList) {
            if (currentYear != year) {
                sum = sum + client.getValue();
                client.setValue(sum);
            } else {

                if (outList.indexOf(client) > currentMonth) {
                    client.setValue(0);
                } else {
                    sum = sum + client.getValue();
                    client.setValue(sum);
                }
            }
        }

        return outList;
    }

    @Override
    public List<ClientsPerMonth> getClientTypePercentage() {

        List<Client> clients = clientRepository.findAll();

        List<ClientsPerMonth> outList = new ArrayList<>();

        outList.add(new ClientsPerMonth("Individual", clients.stream().filter(p -> p.getClient_type().equals("Individual")).collect(Collectors.toList()).size()));
        outList.add(new ClientsPerMonth("Company", clients.stream().filter(p -> p.getClient_type().equals("Company")).collect(Collectors.toList()).size()));

        return outList;
    }

    @Override
    public List<List<Client>> getClientsPerStatusTypes() {

        List<Client> newOnes = clientRepository.findAll().stream().filter(c -> c.getStatus().equals(ClientStatusTypes.NewLead)).collect(Collectors.toList());
        List<Client> contactOnes = clientRepository.findAll().stream().filter(c -> c.getStatus().equals(ClientStatusTypes.ContactMade)).collect(Collectors.toList());
        List<Client> qualifiedOnes = clientRepository.findAll().stream().filter(c -> c.getStatus().equals(ClientStatusTypes.Qualified)).collect(Collectors.toList());
        List<Client> negotiationOnes = clientRepository.findAll().stream().filter(c -> c.getStatus().equals(ClientStatusTypes.InNegotiation)).collect(Collectors.toList());
        List<Client> closedOnes = clientRepository.findAll().stream().filter(c -> c.getStatus().equals(ClientStatusTypes.Closed)).collect(Collectors.toList());

        return Arrays.asList(newOnes, contactOnes, qualifiedOnes, negotiationOnes, closedOnes);
    }

    @Override
    public List<Long> getClientsByGroupId(Long id) {

        Query q = em.createNativeQuery("SELECT a.client_id FROM client_group a WHERE a.group_id = ?");
        q.setParameter(1, id);
        List<BigInteger> list = q.getResultList();
        List<Long> outList = new ArrayList<>();

        for (BigInteger value : list) {
            outList.add(value.longValue());
        }

        return outList;
    }

}
