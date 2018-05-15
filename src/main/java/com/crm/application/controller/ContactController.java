package com.crm.application.controller;

import com.crm.application.model.Contact;
import com.crm.application.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact/add/{id}", method = RequestMethod.POST)
    public ResponseEntity createContact(@PathVariable("id") Long id, @RequestBody Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            contactService.addContact(id, contact);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/contact", method = RequestMethod.PUT)
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            contactService.updateContact(contact);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Long id) {
        if (contactService.getContactById(id).isPresent()) {
            contactService.deleteContactById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {

        if (!contactService.getContactById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contactService.getContactById(id).get(), HttpStatus.OK);
    }

}



