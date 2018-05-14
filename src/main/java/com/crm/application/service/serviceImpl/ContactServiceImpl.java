package com.crm.application.service.serviceImpl;

import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crm.application.model.Contact;
import com.crm.application.service.ContactService;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ClientRepository clientRepository) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return Optional.ofNullable(contactRepository.findOneById(id));
    }

    @Override
    public void addContact(Long id, Contact contact) {
        contact.setClient(clientRepository.findOne(id));
        contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        Contact temp = contactRepository.findOne(contact.getId());
        temp.setEmail(contact.getEmail());
        temp.setName(contact.getName());
        temp.setSurname(contact.getSurname());
        temp.setDescription(contact.getDescription());
        temp.setPhone(contact.getPhone());
        temp.setPosition(contact.getPhone());

        return contactRepository.save(temp);
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.delete(id);
    }

}
