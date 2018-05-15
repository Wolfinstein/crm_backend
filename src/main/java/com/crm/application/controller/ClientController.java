package com.crm.application.controller;

import com.crm.application.model.Client;
import com.crm.application.service.ClientService;
import com.crm.application.validator.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    private final ClientService clientService;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
    }

    @InitBinder("client")
    public void initClientBinder(WebDataBinder binder) {
        binder.addValidators(clientValidator);
    }


    @GetMapping(value = "/clients/no-filter")
    public ResponseEntity getAllClientsNoFilter() {

        if (clientService.getAllClients().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clientService.getAllClients().stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId())).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity getAllClients(@RequestParam(value = "client_type", required = false) String client_type,
                                        @RequestParam(value = "Phone", required = false) String Phone,
                                        @RequestParam(value = "Email", required = false) String Email,
                                        @RequestParam(value = "Nip", required = false) String Nip,
                                        @RequestParam(value = "PESEL", required = false) String PESEL,
                                        @RequestParam(value = "Name", required = false) String Name) {

        List<Client> filteredClients = clientService.getAllClientsWithParams(client_type, Email, Phone, Nip, PESEL, Name);

        if (filteredClients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(filteredClients, HttpStatus.OK);
    }

    @GetMapping(value = "/clients/pipeline")
    public ResponseEntity getClientsForPipeline() {
        if (clientService.getClientsPerStatusTypes().isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientService.getClientsPerStatusTypes(), HttpStatus.OK);
    }


    @GetMapping(value = "client/{id}")
    public ResponseEntity getClient(@PathVariable("id") Long id) {

        if (clientService.getClientById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientService.getClientById(id).get(), HttpStatus.OK);
    }

    @PostMapping(value = "/client")
    public ResponseEntity createClient(@RequestBody Client client) {

        switch (client.getClient_type()) {
            case "Individual":
                if (clientService.getClientByPesel(client.getPesel()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    clientService.saveClient(client);
                    return new ResponseEntity<>(client, HttpStatus.CREATED);
                }
            case "Company":
                if (clientService.getClientByNip(client.getNip()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                if (clientService.getClientByRegon(client.getRegon()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    clientService.saveClient(client);
                    return new ResponseEntity<>(client, HttpStatus.CREATED);
                }
            default:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/client/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Long id) {
        if (clientService.getClientById(id).isPresent()) {
            clientService.deleteClient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/client/edit/{id}")
    public ResponseEntity<Client> editClient(@PathVariable("id") Long id, @Valid @RequestBody Client client, BindingResult bindingResult) {
        Client clientBeforeUpdate = clientService.getClientById(id).get();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (clientBeforeUpdate.getVersion().getTime() != client.getVersion().getTime()) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            clientService.updateClient(id, client);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "statistics/clients/{year}")
    public ResponseEntity getClientsNumberStatistics(@PathVariable("year") Integer year) {

        if (clientService.getClientsPerMonthStatistics(year).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientService.getClientsPerMonthStatistics(year), HttpStatus.OK);
    }

    @GetMapping(value = "statistics/clients/percentage")
    public ResponseEntity getPercentageStatistics() {
        if (clientService.getClientTypePercentage().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientService.getClientTypePercentage(), HttpStatus.OK);
    }

    @PutMapping(value = "update/status/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") Long id, @RequestBody String status) {
        if (id == null || status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.updateClientStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
