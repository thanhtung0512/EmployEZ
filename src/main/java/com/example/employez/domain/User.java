package com.example.employez.domain;

public abstract class User {
    private int id;
    private String passwordHash;
    private String email;

    public User(int id, String passwordHash, String email) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
