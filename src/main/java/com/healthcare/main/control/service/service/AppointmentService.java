package com.healthcare.main.control.service.service;

import com.healthcare.main.entity.model.Appointment;
import com.healthcare.main.entity.model.Doctor;
import com.healthcare.main.entity.model.Patient;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    Appointment getAppointmentById(Long id);
    List<Appointment> getAppointments();
    List<Appointment> getAppointmentsByPatient(Patient patient);
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
    List<Appointment> getAppointmentsDoctorAndPatient(Doctor doctor, Patient patient);
    List<Appointment> findAllByEndTimeGreaterThan(Date end_date);
    List<Appointment> findAllByEndTimeLessThanEqual(Date end_date);
    List<Appointment> findAllByTookPlace(boolean took_place);
    List<Appointment> findAllByEndTimeLessThanEqualAndTookPlace(Date end_date, boolean took_place);
    List<Appointment> findAllByDoctorAndEndTimeGreaterThan(Doctor doctor, Date end_date);
    Integer countAllByStartTimeBetweenAndDoctorOrStartTimeBetweenAndPatient(Date start_date1, Date end_date1, Doctor doctor, Date start_date2, Date end_date2, Patient patient);
    public Integer countAllByStartTimeBetweenAndDoctorOrStartOrPatient(Date startDate, Date endDate, Doctor doctor, Patient patient);
    Appointment save(Appointment appointment);
    public void deleteAppointment(Appointment appointment);
    public  void deleteAppointments();
}
