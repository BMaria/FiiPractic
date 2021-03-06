package com.healthcare.main.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Table(name="appointment")
@JsonIgnoreProperties(value = {"patient", "doctor", "canceledAppointment"})
public class Appointment
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name="start_time")
    private Date startTime;

    @Temporal(TemporalType.DATE)
    @Column(name="end_time")
    private Date endTime;

    @Column(name = "cause")
    @Size(min = 1, max = 100, message = "Cause must be between 1 and 100 characters")
    private String cause;

    @Column(name = "took_place")
    private Boolean tookPlace = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CanceledAppointment canceledAppointment = new CanceledAppointment();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Boolean getTookPlace() {
        return tookPlace;
    }

    public void setTookPlace(Boolean tookPlace) {
        this.tookPlace = tookPlace;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public CanceledAppointment getCanceledAppointment() {
        return canceledAppointment;
    }

    public void setCanceledAppointment(CanceledAppointment canceledAppointment) {
        this.canceledAppointment = canceledAppointment;
    }
}

