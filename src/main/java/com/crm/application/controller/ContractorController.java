package com.crm.application.controller;

import com.crm.application.model.Contractor;
import com.crm.application.service.ContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ContractorController {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @RequestMapping(value = "/contractor/add/{id}", method = RequestMethod.POST)
    public ResponseEntity createContractor(@PathVariable("id") Long id, @RequestBody Contractor contractor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            contractorService.addContractor(id, contractor);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.PUT)
    public ResponseEntity<Contractor> updateContractor(@Valid @RequestBody Contractor contractor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            contractorService.updateContractor(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/contractor/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContractor(@PathVariable("id") Long id) {
        if (contractorService.getContractorById(id).isPresent()) {
            contractorService.deleteContractorById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/contractor/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contractor> getContractor(@PathVariable Long id) {
        if (!contractorService.getContractorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contractorService.getContractorById(id).get(), HttpStatus.OK);
    }


}
