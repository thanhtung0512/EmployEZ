package com.example.employez.domain.address;

public abstract class Address {

    int id;
    private String city;
    private String state;
    private int zipcode;

    public Address(String city, String state, int zipcode) {
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public Address() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
