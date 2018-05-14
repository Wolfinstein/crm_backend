package com.crm.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crm.application.model.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    <T> Address findById(Long id);

}
