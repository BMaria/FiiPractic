package com.healthcare.main.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    @Size(min = 2, max = 25, message = "Street must be between 2 and 25 characters")
    private String street;

    @Column(name = "city")
    @Size(min = 2, max = 25, message = "City must be between 2 and 25 characters")
    private String city;

    @Column(name = "country")
    @Size(min = 2, max = 25, message = "Country must be between 2 and 25 characters")
    private String country;

    @Column(name = "postal_code")
    @Size(min = 2, max = 15, message = "Postal code must be between 2 and 15 characters")
    private String postal_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
