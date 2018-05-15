package com.crm.application.controller;

import com.crm.application.model.Client;
import com.crm.application.service.ClientService;
import com.crm.application.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    private final GroupService groupService;
    private final ClientService clientService;


    public GroupController(GroupService groupService, ClientService clientService) {
        this.groupService = groupService;
        this.clientService = clientService;
    }


    @RequestMapping(value = "/groups/all", method = RequestMethod.GET)
    public ResponseEntity getGroups() {
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/clients/{id}", method = RequestMethod.GET)
    public ResponseEntity getClientByGroupId(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientsByGroupId(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/groups/change/{id}", method = RequestMethod.POST)
    public ResponseEntity updateGroupMembers(@PathVariable("id") Long id, @RequestBody List<Client> clients) {
        groupService.changeGroupMembers(id, clients);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody String name) {
        if (name.length() < 5 || groupService.getGroupByName(name).isPresent()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        groupService.addNewGroup(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@PathVariable Long id, @RequestBody String name) {
        if (name.length() < 5) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }   // substring bo przychodzi w postaci "name"
        if (groupService.getGroupByName(name).isPresent() && !groupService.getGroupById(id).get().getName().equals(name.substring(1, name.length() - 1))) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        groupService.updateGroup(name, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable("id") Long id) {

        if (!groupService.getGroupById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/dropdown", method = RequestMethod.GET)
    public ResponseEntity getDropdownData() {
        return new ResponseEntity<>(groupService.getDropdownData(), HttpStatus.OK);
    }


    @RequestMapping(value = "/groups/data", method = RequestMethod.GET)
    public ResponseEntity getFilteredClients(@RequestParam(name = "listOfGroupId", required = false) String listOfGroupId) {
        return new ResponseEntity<>(clientService.getClientsWithGroupFilter(listOfGroupId), HttpStatus.OK);
    }


}

