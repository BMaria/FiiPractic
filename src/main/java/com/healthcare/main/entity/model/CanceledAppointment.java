package com.healthcare.main.entity.model;

import javax.persistence.*;

@Entity
@Table(name = "canceled_appointment")
public class CanceledAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "canceled")
    private Boolean canceled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }


}
