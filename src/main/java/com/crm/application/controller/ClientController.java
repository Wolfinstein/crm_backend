package com.crm.application.controller;

import com.crm.application.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.crm.application.model.Client;
import com.crm.application.service.ClientService;
import com.crm.application.validator.ClientValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    private static final String clientModel = "client";
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, ClientRepository clientRepository, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
    }

    @InitBinder(clientModel)
    public void initClientBinder(WebDataBinder binder) {
        binder.addValidators(clientValidator);
    }


    @GetMapping(value = "/clients/no-filter")
    public ResponseEntity clientWithoutFilter() {

        if (clientRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clientRepository.findAll().stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId())).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity listAllClients(@RequestParam(value = "client_type", required = false)
                                                 String client_type,
                                         @RequestParam(value = "Phone", required = false)
                                                 String Phone,
                                         @RequestParam(value = "Email", required = false)
                                                 String Email,
                                         @RequestParam(value = "Nip", required = false)
                                                 String Nip,
                                         @RequestParam(value = "PESEL", required = false)
                                                 String PESEL,
                                         @RequestParam(value = "Name", required = false)
                                                 String Name) {
        List<Client> clients = (List<Client>) clientService.findAllClientFilter(client_type, Email, Phone, Nip, PESEL, Name);
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/clients/pipeline")
    public ResponseEntity listAllClients() {

        if (clientService.clientsPerStatusTypes().isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientService.clientsPerStatusTypes(), HttpStatus.OK);
    }


    @GetMapping(value = "client/{id}")
    public ResponseEntity getClient(@PathVariable("id") Long id) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return new ResponseEntity<>("No client found for Id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping(value = "/client")
    public ResponseEntity createClient(@RequestBody Client client) {
        if (client.getClient_type().equals("Individual")) {
            if (clientService.getClientByPesel(client.getPesel()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                clientService.saveClient(client);
                return new ResponseEntity<>(client, HttpStatus.CREATED);
            }
        }
        if (client.getClient_type().equals("Company")) {
            if (clientService.getClientByNip(client.getNip()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            if (clientService.getClientByRegon(client.getRegon()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                clientService.saveClient(client);
                return new ResponseEntity<>(client, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/client/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable("id") long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/client/edit/{id}")
    public ResponseEntity<Client> editClient(@PathVariable("id") long id, @Valid @RequestBody Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (clientRepository.findOne(id).getVersion().getTime() != client.getVersion().getTime()) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            clientService.updateClient(id, client);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "statistics/clients/{year}")
    public ResponseEntity getStats(@PathVariable("year") Integer year) {

        if (clientService.clientsPerMonthStatistics(year).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientService.clientsPerMonthStatistics(year), HttpStatus.OK);
    }

    @GetMapping(value = "statistics/clients/percentage")
    public ResponseEntity getStats() {

        if (clientService.clientTypePercentage().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientService.clientTypePercentage(), HttpStatus.OK);
    }

    @PutMapping(value = "update/status/{id}")
    public ResponseEntity setStatus(@PathVariable("id") Long id, @RequestBody String status) {
        if (id == null || status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.updateStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
