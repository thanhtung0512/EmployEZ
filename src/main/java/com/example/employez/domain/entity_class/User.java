package com.example.employez.domain.entity_class;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String passwordHash;

    @Column
    private String email;

    public User( String passwordHash, String email) {
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public User() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
