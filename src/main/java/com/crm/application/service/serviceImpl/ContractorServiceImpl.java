package com.crm.application.service.serviceImpl;

import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.ContractorRepository;
import org.springframework.stereotype.Service;
import com.crm.application.model.Contractor;
import com.crm.application.service.ContractorService;

import java.util.Optional;

@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ClientRepository clientRepository;

    public ContractorServiceImpl(ContractorRepository contractorRepository, ClientRepository clientRepository) {
        this.contractorRepository = contractorRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Optional<Contractor> getContractorById(Long id) {
        return Optional.ofNullable(contractorRepository.findById(id));
    }

    @Override
    public void addContractor(Long id, Contractor contractor) {
        contractor.setClient(clientRepository.findOne(id));
        contractorRepository.save(contractor);
    }

    @Override
    public Contractor updateContractor(Contractor contractor) {
        Contractor temp = contractorRepository.findById(contractor.getId());
        temp.setDescription(contractor.getDescription());
        temp.setName(contractor.getName());

        return contractorRepository.save(temp);
    }

    @Override
    public void deleteContractorById(Long id) {
        contractorRepository.delete(id);
    }

}
