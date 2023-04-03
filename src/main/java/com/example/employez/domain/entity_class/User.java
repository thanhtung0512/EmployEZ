package com.example.employez.domain.entity_class;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY
            ,cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REFRESH})
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "fk_user")},
            inverseJoinColumns = {@JoinColumn(name = "fk_role")})
    private Set<Role> roles = new HashSet<>();


}
