package com.crm.application.service;


import com.crm.application.model.Contractor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ContractorService {

    Optional<Contractor> getContractorById(Long id);

    void addContractor(Long id, Contractor contractor);

    Contractor updateContractor(Contractor contractor);

    void deleteContractorById(Long id);


}
