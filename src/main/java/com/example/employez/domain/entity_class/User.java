package com.example.employez.domain.entity_class;


 import jakarta.persistence.*;

 import java.util.HashSet;
 import java.util.Set;

@Entity
@Table(name = "user")
public  class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REFRESH})
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "fk_user")},
            inverseJoinColumns = {@JoinColumn(name = "fk_role")})
    private Set<Role> roles = new HashSet<>();

    public User(int id, String passwordHash, String email, Set<Role> roles) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.email = email;
        this.roles = roles;
    }

    public User(String passwordHash, String email) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
