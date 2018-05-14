package com.crm.application.model;

import com.crm.application.utilModels.ClientStatusTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Transactional
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_type", length = 50)
    private String client_type;

    @NotEmpty
    @Email
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @NotEmpty
    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "description", length = 50)
    private String description;

    @NotNull
    @Column(name = "create_timestamp")
    private Date createTimeStamp;

    @Column(name = "firstName", length = 150)
    private String firstName;

    @Column(name = "lastName", length = 150)
    private String lastName;

    @NotNull
    @Column(name = "pesel", length = 150)
    private String pesel;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "nip", length = 20)
    private String nip;

    @Column(name = "regon", length = 20)
    private String regon;

    @Column(name = "type", length = 260)
    private String type;

    @Column(name = "website", length = 50)
    private String website;

    @Column(name = "trade", length = 50)
    private String trade;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private ClientStatusTypes status;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "version")
    private Date version;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Document> documents = new ArrayList<Document>();

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Address> addresses = new ArrayList<Address>();

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<Contact>();

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Contractor> contractors = new ArrayList<Contractor>();

    @JsonIgnore
    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Activity> activities = new ArrayList<Activity>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "client_group",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups = new HashSet<>();

    public Client(String client_type, String email, String phone, String description, String firstName, String lastName, String pesel, String name, String nip, String regon, String type, String website, String trade, List<Document> documents, List<Address> addresses, List<Contact> contacts, List<Contractor> contractors) {
        this.client_type = client_type;
        this.email = email;
        this.phone = phone;
        this.description = description;
        setCreateTimeStamp();
        this.status = ClientStatusTypes.ContactMade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.type = type;
        this.website = website;
        this.trade = trade;
        this.documents = documents;
        this.addresses = addresses;
        this.contacts = contacts;
        this.contractors = contractors;
    }

    public Client() {
        setCreateTimeStamp();
    }

    public Client(String client_type, String email, String phone, String description, String firstName, String lastName, String pesel, String name, String nip, String regon, String type, String website, String trade) {
        this.client_type = client_type;
        this.email = email;
        this.phone = phone;
        this.description = description;
        setCreateTimeStamp();
        this.status = ClientStatusTypes.ContactMade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.type = type;
        this.website = website;
        this.trade = trade;
    }

    public Client(String client_type, String email, String phone, String description, Date createTimeStamp, String firstName, String lastName, String pesel, String name, String nip, String regon, String type, String website, String trade, List<Document> documents, List<Address> addresses, List<Contact> contacts, List<Contractor> contractors, List<Activity> activities, Set<Group> groups) {
        this.groups = groups;
        this.client_type = client_type;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.status = ClientStatusTypes.ContactMade;
        setCreateTimeStamp();
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.type = type;
        this.website = website;
        this.trade = trade;
        this.documents = documents;
        this.addresses = addresses;
        this.contacts = contacts;
        this.contractors = contractors;
        this.activities = activities;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(List<Contractor> contractors) {
        this.contractors = contractors;
    }

    public Date getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public ClientStatusTypes getStatus() {
        return status;
    }

    public void setStatus(ClientStatusTypes status) {
        this.status = status;
    }

    public void setCreateTimeStamp() {
        java.util.Date date = new Date();
        this.createTimeStamp = date;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", client_type='" + client_type + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", createTimeStamp='" + createTimeStamp + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", PESEL='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", nip='" + nip + '\'' +
                ", regon='" + regon + '\'' +
                ", type='" + type + '\'' +
                ", website='" + website + '\'' +
                ", trade='" + trade + '\'' +
                '}';
    }

}
