package com.crm.application.step;

import com.crm.application.model.Client;
import com.crm.application.repository.dao.ClientDao;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


public class Writer implements ItemWriter<Client> {

    private final ClientDao clientDao;

    public Writer(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void write(List<? extends Client> clients) throws Exception {

        clientDao.insert(clients);
    }

}
