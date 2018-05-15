package com.crm.application.repository;

import com.crm.application.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    <T> Contractor findById(Long id);

}
