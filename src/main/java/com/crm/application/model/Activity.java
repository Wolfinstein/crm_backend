package com.crm.application.model;

import com.crm.application.utilModels.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Table(name = "activity")
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    @Column(name = "type_activity", length = 50)
    @Enumerated(EnumType.STRING)
    private ActivityType type_activity;

    @NotEmpty(message = "{description.empty}")
    @Column(name = "description_activity", length = 100)
    private String description_activity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "activity_date", length = 50)
    private Date activity_date;

    public Activity() {
        setActivity_date();
    }

    public Activity(User user, Client client, ActivityType type_activity, String description_activity) {
        this.type_activity = type_activity;
        this.description_activity = description_activity;
        this.user = user;
        this.client = client;
        setActivity_date();
    }

    public Date getActivity_date() {
        return activity_date;
    }

    public void setActivity_date() {
        java.util.Date date = new Date();
        this.activity_date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ActivityType getType_activity() {
        return type_activity;
    }

    public void setType_activity(ActivityType type_activity) {
        this.type_activity = type_activity;
    }

    public String getDescription_activity() {
        return description_activity;
    }

    public void setDescription_activity(String description_activity) {
        this.description_activity = description_activity;
    }
}