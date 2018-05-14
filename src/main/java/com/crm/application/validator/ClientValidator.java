package com.crm.application.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.crm.application.model.Client;
import com.crm.application.service.ClientService;

@Component
public class ClientValidator implements Validator {

    private final ClientService clientService;

    public ClientValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Client.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        validatePesel(errors, client);
        validateNip(errors, client);
        validateRegon(errors, client);
    }

    private void validatePesel(Errors errors, Client client) {
        if (client.getClient_type().equals("Individual"))
            if (client.getPesel() == null || client.getPesel().isEmpty()) {
                errors.reject("pesel.empty");
            } else {
                if (clientService.getClientByPesel(client.getPesel()).isPresent() && clientService.getClientByPesel(client.getPesel()).get().getId() != client.getId()) {
                    errors.reject("pesel.exists");
                }
            }
    }

    private void validateNip(Errors errors, Client client) {
        if (client.getClient_type().equals("Company"))
            if (client.getNip() == null || client.getNip().isEmpty()) {
                errors.reject("nip.empty");
            } else {
                if (clientService.getClientByNip(client.getNip()).isPresent() && clientService.getClientByNip(client.getNip()).get().getId() != client.getId()) {
                    errors.reject("nip.exists");
                }
            }
    }

    private void validateRegon(Errors errors, Client client) {
        if (client.getClient_type().equals("Company"))
            if (client.getRegon() == null || client.getRegon().isEmpty()) {
                errors.reject("regon.empty");
            } else {
                if (clientService.getClientByRegon(client.getRegon()).isPresent() && clientService.getClientByRegon(client.getRegon()).get().getId() != client.getId()) {
                    errors.reject("regon.exists");
                }
            }
    }
}
