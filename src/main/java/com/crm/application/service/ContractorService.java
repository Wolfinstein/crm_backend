package com.crm.application.service;


import org.springframework.stereotype.Service;
import com.crm.application.model.Contractor;

import java.util.Optional;

@Service
public interface ContractorService {

    Optional<Contractor> getContractorById(Long id);

    void addContractor(Long id, Contractor contractor);

    Contractor updateContractor(Contractor contractor);

    void deleteContractorById(Long id);


}
