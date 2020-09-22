package com.buzzardsview.readitcheck.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "google_id")
    private User user;
    private long timestamp;

    public Content() {
    }

    public Content(User user) {
        this.user = user;
        this.timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
