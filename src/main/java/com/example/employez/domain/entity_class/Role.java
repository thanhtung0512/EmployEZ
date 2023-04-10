package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /*@ManyToMany(fetch = FetchType.LAZY,mappedBy = "roles")
    private Set<User> users = new HashSet<>();*/


    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ROLE name;





}
