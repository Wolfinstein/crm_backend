package com.crm.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crm.application.model.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    <T> Contractor findById(Long id);

}
