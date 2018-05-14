package com.crm.application.service;

import org.springframework.stereotype.Service;
import com.crm.application.model.Address;

import java.util.Optional;

@Service
public interface AddressService {

    Optional<Address> getAddressById(Long id);

    void addAddress(Long id, Address address);

    Address updateAddress(Address address);

    void deleteAddressById(Long id);

}
