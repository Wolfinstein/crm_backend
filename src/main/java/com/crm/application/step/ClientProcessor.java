package com.crm.application.step;

import com.crm.application.model.Client;
import org.springframework.batch.item.ItemProcessor;


public class ClientProcessor implements ItemProcessor<Client, Client> {

    @Override
    public Client process(Client client) throws Exception {
        return client;
    }
}
