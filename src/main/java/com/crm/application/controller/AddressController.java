package com.crm.application.controller;

import com.crm.application.model.Address;
import com.crm.application.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/address/add/{id}", method = RequestMethod.POST)
    public ResponseEntity createAddress(@PathVariable("id") Long id, @RequestBody Address address, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            addressService.addAddress(id, address);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/address", method = RequestMethod.PUT)
    public ResponseEntity updateAddress(@Valid @RequestBody Address address, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            addressService.updateAddress(address);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAddress(@PathVariable("id") Long id) {
        if (addressService.getAddressById(id).isPresent()) {
            addressService.deleteAddressById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public ResponseEntity getAddress(@PathVariable Long id) {
        if (!addressService.getAddressById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addressService.getAddressById(id).get(), HttpStatus.OK);
    }


}



