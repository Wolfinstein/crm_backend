package com.crm.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.crm.application.model.Client;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ClientRepository extends JpaRepository<Client, Long>, PagingAndSortingRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    <T> Client findOneByPesel(String pesel);

    <T> Client findOneByNip(String nip);

    <T> Client findOneByRegon(String regon);

    Optional<Client> findByEmail(String email);


}
