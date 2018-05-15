package com.crm.application.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Lob
    @Column(name = "content")
    private byte[] content;

    public Document(Client client, String name, String extension, byte[] content) {
        this.client = client;
        this.name = name;
        this.extension = extension;
        this.content = content;
    }

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }


}
