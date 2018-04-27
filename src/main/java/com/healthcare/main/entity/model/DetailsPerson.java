package com.healthcare.main.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@MappedSuperclass
public class DetailsPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Email email;


    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PhoneNumber phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLastName(String last_Name) {
        this.lastName = last_Name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

}
