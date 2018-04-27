package com.healthcare.main.entity.repository;

import com.healthcare.main.entity.model.Appointment;
import com.healthcare.main.entity.model.Doctor;
import com.healthcare.main.entity.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDoctorAndPatient(Doctor doctor, Patient patient);
    List<Appointment> findAllByDoctor(Doctor doctor);
    List<Appointment> findAllByPatient(Patient patient);
    List<Appointment> findAllByEndTimeGreaterThan(Date end_date);
    List<Appointment> findAllByEndTimeLessThanEqual(Date end_date);
    List<Appointment> findAllByTookPlace(boolean took_place);
    List<Appointment> findAllByEndTimeLessThanEqualAndTookPlace(Date end_date, boolean took_place);
    List<Appointment> findAllByDoctorAndEndTimeGreaterThan(Doctor doctor, Date end_date);

    Integer countAllByStartTimeBetweenAndDoctorOrStartTimeBetweenAndPatient(Date start_date1, Date end_date1, Doctor doctor, Date start_date2, Date end_date2, Patient patient);
}
