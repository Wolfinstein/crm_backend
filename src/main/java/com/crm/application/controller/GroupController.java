package com.crm.application.controller;

import com.crm.application.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.crm.application.model.Client;
import com.crm.application.service.ClientService;
import com.crm.application.service.GroupService;

import java.util.List;

@RestController
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final ClientService clientService;


    public GroupController(GroupRepository groupRepository, GroupService groupService, ClientService clientService) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.clientService = clientService;
    }


    @RequestMapping(value = "/groups/all", method = RequestMethod.GET)
    public ResponseEntity getGroups() {

        return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/clients/{id}", method = RequestMethod.GET)
    public ResponseEntity getClientByGroupId(@PathVariable Long id) {

        return new ResponseEntity<>(clientService.getClientsByGroupId(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/groups/change/{id}", method = RequestMethod.POST)
    public ResponseEntity updateMembers(@PathVariable("id") Long id, @RequestBody List<Client> clients) {

        groupService.changeGroupMembers(id, clients);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody String name) {

        if (name.length() < 5 || groupRepository.findByName(name.substring(1, name.length() - 1)).isPresent()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        groupService.addNewGroup(name.substring(1, name.length() - 1));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody String name) {

        if (name.length() < 5) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (groupRepository.findByName(name.substring(1, name.length() - 1)).isPresent() && !groupRepository.findOne(id).getName().equals(name.substring(1, name.length() - 1))) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        groupService.updateGroup(name.substring(1, name.length() - 1), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {

        if (!groupRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        groupRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/dropdown", method = RequestMethod.GET)
    public ResponseEntity getDropdownData() {

        return new ResponseEntity<>(groupService.getDropdownData(), HttpStatus.OK);
    }


    @RequestMapping(value = "/groups/data", method = RequestMethod.GET)
    public ResponseEntity getFilteredClients(@RequestParam(name = "listOfGroupId", required = false) String listOfGroupId) {
        System.out.println(listOfGroupId);
        return new ResponseEntity<>(clientService.getClientsWithGroupFilter(listOfGroupId), HttpStatus.OK);
    }


}

