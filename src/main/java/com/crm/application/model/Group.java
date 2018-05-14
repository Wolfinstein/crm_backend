package com.crm.application.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "groups")
@Entity
public class Group {

    @ManyToMany(mappedBy = "groups")
    private Set<Client> clients = new HashSet<>();

    @Length(min = 3)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public Group(Set<Client> clients, String name) {
        this.clients = clients;
        this.name = name;
    }

    @PreRemove
    private void removeGroupsFromClients() {
        for (Client c : clients) {
            c.getGroups().remove(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
