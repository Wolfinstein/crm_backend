package com.crm.application.model;

import javax.persistence.*;

@Table(name = "events")
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "startday", length = 100)
    private String start;

    @Column(name = "endday", length = 100)
    private String end;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "color")
    private String color;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Event(String start, String end, String title, String color, User user) {
        this.start = start;
        this.end = end;
        this.title = title;
        this.color = color;
        this.user = user;
    }

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
