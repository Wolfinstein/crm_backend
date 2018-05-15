package com.crm.application.service;

import com.crm.application.model.Client;
import com.crm.application.model.Group;
import com.crm.application.utilModels.user.GroupDropdownModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GroupService {

    void changeGroupMembers(Long id, List<Client> clients);

    void addNewGroup(String name);

    void updateGroup(String name, Long id);

    List<GroupDropdownModel> getDropdownData();

    List<Group> getAllGroups();

    Optional<Group> getGroupById(Long id);

    Optional<Group> getGroupByName(String name);

    void deleteGroup(Long id);
}
