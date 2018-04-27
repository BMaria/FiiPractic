package com.healthcare.main.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="patient")
@JsonIgnoreProperties(value = {"appointments"})
public class Patient extends DetailsPerson
{

    @Column(name = "patient_age", nullable = false)
    private Long patientAge;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "patient",
            fetch = FetchType.LAZY)
    private List<Appointment> appointmentList = new ArrayList<>();

    public Long getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Long patientAge) {
        this.patientAge = patientAge;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
