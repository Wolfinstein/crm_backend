package com.crm.application.service.serviceImpl;

import com.crm.application.model.Address;
import com.crm.application.repository.AddressRepository;
import com.crm.application.repository.ClientRepository;
import com.crm.application.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public AddressServiceImpl(AddressRepository addressRepository, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Optional<Address> getAddressById(Long id) {
        return Optional.ofNullable(addressRepository.findById(id));
    }

    @Override
    public void addAddress(Long id, Address address) {
        address.setClient(clientRepository.findOne(id));
        addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        Address temp = addressRepository.findOne(address.getId());
        temp.setAddressType(address.getAddressType());
        temp.setCity(address.getCity());
        temp.setCountry(address.getCountry());
        temp.setHouseNumber(address.getHouseNumber());
        temp.setState(address.getState());
        temp.setStreet(address.getStreet());
        temp.setZipCode(address.getZipCode());

        return addressRepository.save(temp);
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRepository.delete(id);
    }

}
