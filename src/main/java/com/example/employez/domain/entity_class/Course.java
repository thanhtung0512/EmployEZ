package com.example.employez.domain.entity_class;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Course implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "rating")
    private double rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "covers",
            joinColumns = {@JoinColumn(name = "fk_course")},
            inverseJoinColumns = {@JoinColumn(name = "fk_skill")})
    private Set<Skill> skillSet;

}
