package com.example.employez.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {



    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserForm(String email, String password, String confirmPassword, String role) {

        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }


    // getters and setters

    public UserForm() {
    }

    ;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserForm( String email, String password, String confirmPassword) {

        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;


    }

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordMatch() {
        return password.equals(confirmPassword);
    }
}
