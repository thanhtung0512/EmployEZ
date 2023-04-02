package com.example.employez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
@EnableJpaRepositories(value = "com.example.employez.repository")
public class EmployezApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployezApplication.class, args);
    }


}
