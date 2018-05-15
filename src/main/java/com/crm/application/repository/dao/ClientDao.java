package com.crm.application.repository.dao;

import com.crm.application.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDao {
    void insert(List<? extends Client> clients);

}
