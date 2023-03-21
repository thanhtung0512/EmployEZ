package com.example.employez.domain;

import com.example.employez.domain.address.Address;
import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Employee extends User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;
    private int desiredSalary;
    private Address currentAddress;
    private int yearOfExperience;

    private String jobTitle;


    @OneToMany(mappedBy = "employee")
    private Set<Resume> resumes;

    public Employee() {

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Employee(int id, String passwordHash, String email, String firstName, String lastName, int desiredSalary, Address currentAddress, int yearOfExperience, String jobTitle) {
        super(id, passwordHash, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredSalary = desiredSalary;
        this.currentAddress = currentAddress;
        this.yearOfExperience = yearOfExperience;
        this.jobTitle = jobTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(int desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }
}
