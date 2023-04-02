package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ROLE name;





}
