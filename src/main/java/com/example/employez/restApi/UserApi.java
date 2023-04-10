package com.example.employez.restApi;

import com.example.employez.domain.entity_class.User;
import com.example.employez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserApi {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/byid/{id}")
    Optional<User> getUser(@PathVariable(name = "id") Integer id) {
        return userRepository.findById(id);
    }
}
