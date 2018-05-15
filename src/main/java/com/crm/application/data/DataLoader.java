package com.crm.application.data;

import com.crm.application.model.*;
import com.crm.application.repository.*;
import com.crm.application.utilModels.ActivityType;
import com.crm.application.utilModels.AddressType;
import com.crm.application.utilModels.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private ContactRepository contactRepository;
    private ContractorRepository contractorRepository;
    private AddressRepository addressRepository;
    private ActivityRepository activityRepository;
    private EventRepository eventRepository;
    private GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository, ClientRepository clientRepository,
                      UserRepository userRepository, ContactRepository contactRepository,
                      ContractorRepository contractorRepository, AddressRepository addressRepository,
                      ActivityRepository activityRepository, EventRepository eventRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.contractorRepository = contractorRepository;
        this.addressRepository = addressRepository;
        this.activityRepository = activityRepository;
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
    }

    public void run(ApplicationArguments args) {

        contactRepository.save(new Contact(clientRepository.findOne(1L), "2", "2", "2", "2", "2", "2"));
        contactRepository.save(new Contact(clientRepository.findOne(1L), "32", "21", "23", "21", "32", "21"));

        userRepository.save(new User("Admin", "surname", "admin", "admin@localhost.com", "$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN));
        userRepository.save(new User("Salesman", "surname", "salesman", "salesman@localhost.com", "$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.SALESMAN));
        userRepository.save(new User("Manager", "surname", "manager", "manager@localhost.com", "$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.MANAGER));

        addressRepository.save(new Address(clientRepository.findOne(1L), AddressType.HOME, "Poland", "dsa", "Wars", "Poniatowskiego", "15", "96-040"));
        addressRepository.save(new Address(clientRepository.findOne(1L), AddressType.DELIVERY, "dsa", "dsa", "asd", "das", "ds", "dsa"));

        contractorRepository.save(new Contractor("dsa", "dsa", clientRepository.findOne(1L)));
        contractorRepository.save(new Contractor("dsa321321", "d3213sa", clientRepository.findOne(1L)));

        activityRepository.save(new Activity(userRepository.findOne(1L), clientRepository.findOne(1L), ActivityType.ClientEdited, "Modyfikacja danych klienta dnia 23.02.1990r. Dane edytowane przed usunieciem pliku milenijnego"));
        activityRepository.save(new Activity(userRepository.findOne(1L), clientRepository.findOne(2L), ActivityType.ClientEdited, "Opis2"));
        activityRepository.save(new Activity(userRepository.findOne(2L), clientRepository.findOne(1L), ActivityType.ClientEdited, "Kontakt z klientem dnia 22.03.2220r. Kontakt z klientem dnia 22.03.2220r."));
        activityRepository.save(new Activity(userRepository.findOne(3L), clientRepository.findOne(3L), ActivityType.ContactWithClient, "Kontakt z klientem dnia 22.03.2220r. Modyfikacja danych klienta dnia 23.02.1990r."));
        activityRepository.save(new Activity(userRepository.findOne(3L), clientRepository.findOne(4L), ActivityType.ContactWithClient, "Kontakt z klientem dnia 22.03.2220r. Modyfikacja danych klienta dnia 23.02.1990r."));
        activityRepository.save(new Activity(userRepository.findOne(1L), clientRepository.findOne(4L), ActivityType.ContactWithClient, "Kontakt z klientem dnia 22.03.2220r. Modyfikacja danych klienta dnia 23.02.1990r."));

        eventRepository.save(new Event("2018-04-15T22:00:00.000Z", "2018-04-18T11:05:22.901Z", "Nr. 1 Event", "red", userRepository.findOne(3L)));
        eventRepository.save(new Event("2018-04-10T11:05:22.000Z", "2018-04-20T11:05:22.901Z", "Event nr. 2", "yellow", userRepository.findOne(2L)));
        eventRepository.save(new Event("2018-04-01T11:05:22.000Z", "2018-04-02T11:05:22.901Z", "Event nr. 3", "green", userRepository.findOne(1L)));
        groupRepository.save(new Group("Best"));
        groupRepository.save(new Group("Worst"));
        groupRepository.save(new Group("Medium"));

        Set<Group> group = new HashSet<>();
        group.add(groupRepository.findOne(1L));
        group.add(groupRepository.findOne(2L));


        Client client = clientRepository.findOne(11L);
        Client client2 = clientRepository.findOne(55L);
        Client client3 = clientRepository.findOne(32L);
        Client client4 = clientRepository.findOne(4L);
        client.setGroups(group);
        client2.setGroups(group);
        client3.setGroups(group);
        client4.setGroups(Collections.singleton(groupRepository.findOne(3L)));

        clientRepository.save(client);
        clientRepository.save(client2);
        clientRepository.save(client3);
        clientRepository.save(client4);


    }

}