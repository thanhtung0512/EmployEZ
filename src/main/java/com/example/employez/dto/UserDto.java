package com.example.employez.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
