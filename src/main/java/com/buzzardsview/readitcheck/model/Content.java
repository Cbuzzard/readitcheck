package com.buzzardsview.readitcheck.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
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
