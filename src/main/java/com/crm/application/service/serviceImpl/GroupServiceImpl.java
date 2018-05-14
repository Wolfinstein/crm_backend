package com.crm.application.service.serviceImpl;

import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.GroupRepository;
import com.crm.application.utilModels.user.GroupDropdownModel;
import org.springframework.stereotype.Service;
import com.crm.application.model.Client;
import com.crm.application.model.Group;
import com.crm.application.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ClientRepository clientRepository;

    public GroupServiceImpl(GroupRepository groupRepository, ClientRepository clientRepository) {
        this.groupRepository = groupRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void changeGroupMembers(Long id, List<Client> clients) {

        List<Client> all = clientRepository.findAll();
        Group groupToAdd = groupRepository.findOne(id);
        List<Long> listOfClientId = new ArrayList<>();

        for (Client client : clients) {
            listOfClientId.add(client.getId());
        }

        for (Client client : all) {
            if (listOfClientId.contains(client.getId())) {
                Set<Group> set = client.getGroups();
                if (!set.contains(groupToAdd)) {
                    set.add(groupToAdd);
                    clientRepository.save(client);
                }
            } else {
                Set<Group> set = client.getGroups();
                set.remove(groupToAdd);
                clientRepository.save(client);
            }
        }
    }

    @Override
    public void addNewGroup(String name) {
        Group group = new Group(name);
        groupRepository.save(group);
    }

    @Override
    public void updateGroup(String name, Long id) {
        Group temp = groupRepository.findOne(id);
        temp.setName(name);
        groupRepository.save(temp);
    }

    @Override
    public List<GroupDropdownModel> getDropdownData() {

        List<Group> all = new ArrayList<>(groupRepository.findAll());
        List<GroupDropdownModel> outList = new ArrayList<>();

        for (Group group : all) {
            outList.add(new GroupDropdownModel(group.getId(), group.getName()));
        }
        return outList;

    }


}
