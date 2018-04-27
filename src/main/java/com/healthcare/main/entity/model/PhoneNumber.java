package com.healthcare.main.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="phone_number")
public class PhoneNumber
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    @Pattern(regexp="(07)\\d{8}", message = "Invalid phone number")
    private String phone_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
