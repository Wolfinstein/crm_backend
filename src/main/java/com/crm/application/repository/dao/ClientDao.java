package com.crm.application.repository.dao;

import org.springframework.stereotype.Repository;
import com.crm.application.model.Client;

import java.util.List;

@Repository
public interface ClientDao {
    void insert(List<? extends Client> clients);

}
