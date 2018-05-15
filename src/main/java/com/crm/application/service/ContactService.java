package com.crm.application.service;

import com.crm.application.model.Contact;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ContactService {

    Optional<Contact> getContactById(Long id);

    void addContact(Long id, Contact contact);

    Contact updateContact(Contact contact);

    void deleteContactById(Long id);

}
