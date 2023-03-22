package com.example.employez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EmployezApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployezApplication.class, args);
    }


}
