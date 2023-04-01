package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.ROLE;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ROLE name;


    public Role() {}

    public Role(Long id, Set<User> users, ROLE name) {
        this.id = id;
        this.users = users;
        this.name = name;
    }

    public ROLE getName() {
        return name;
    }

    public void setName(ROLE name) {
        this.name = name;
    }
}
