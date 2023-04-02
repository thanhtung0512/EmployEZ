package com.example.employez.repository;

import com.example.employez.domain.entity_class.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User getUserByEmail(String email);
}
