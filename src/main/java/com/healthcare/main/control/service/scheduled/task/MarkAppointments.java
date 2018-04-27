package com.healthcare.main.control.service.scheduled.task;

import com.healthcare.main.control.service.service.AppointmentService;
import com.healthcare.main.entity.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class MarkAppointments {
    private AppointmentService appointmentService;

    @Autowired
    public MarkAppointments(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }

    //* marks the appointments that already took place at every 5 minutes
    @Scheduled(fixedDelay = 300000)
    public void reportCurrentTime()
    {
        Date current_date = new Date();
        List<Appointment> appointmentList = appointmentService.findAllByEndTimeLessThanEqualAndTookPlace(current_date,false);
        for(Appointment appointment : appointmentList)
        {
            appointment.setTookPlace(true);
            appointmentService.save(appointment);
        }
    }
}
