package com.crm.application.service;

import org.springframework.stereotype.Service;
import com.crm.application.model.Contact;

import java.util.Optional;

@Service
public interface ContactService {

    Optional<Contact> getContactById(Long id);

    void addContact(Long id, Contact contact);

    Contact updateContact(Contact contact);

    void deleteContactById(Long id);

}
