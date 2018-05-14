package com.crm.application.service;

import com.crm.application.utilModels.user.GroupDropdownModel;
import org.springframework.stereotype.Service;
import com.crm.application.model.Client;

import java.util.List;

@Service
public interface GroupService {

    void changeGroupMembers(Long id, List<Client> clients);

    void addNewGroup(String name);

    void updateGroup(String name, Long id);

    List<GroupDropdownModel> getDropdownData();
}
