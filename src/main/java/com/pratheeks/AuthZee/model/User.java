package com.pratheeks.AuthZee.model;

public class User {
    private long id;
    private long level;

    private String username;
    private String password;

    private String name;

    public User(long id, long level, String username, String password, String name) {
        this.id = id;
        this.level = level;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    /** Temporary constructor. For development only! */
    public User(long id, long level) {
        this(id, level, "Name user"+id, "user"+id, "user"+id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
