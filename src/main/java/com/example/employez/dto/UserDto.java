package com.example.employez.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String fullName;
    private String email;
    private String jobTitle;
    private String address;
    private String phone;
    private String university;
    private String country;
    private String state;
    private String firstName;
    private String lastName;
    private List<String> resumePath = new ArrayList<>();
    public void addResumePath(String path) {
        resumePath.add(path);
    }
}
