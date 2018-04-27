package com.healthcare.main.boundry.controller;

import com.healthcare.main.boundry.dto.AppointmentDTO;
import com.healthcare.main.boundry.exception.BadRequestException;
import com.healthcare.main.boundry.exception.NotFoundException;
import com.healthcare.main.boundry.mapper.AppointmentMapper;
import com.healthcare.main.control.service.service.AppointmentService;
import com.healthcare.main.control.service.service.DoctorService;
import com.healthcare.main.control.service.service.EmailService;
import com.healthcare.main.control.service.service.PatientService;
import com.healthcare.main.entity.model.*;
import com.healthcare.main.util.EmailGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping(value="appointments")
public class AppointmentController {

    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private EmailService emailService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService,
                                 PatientService patientService, EmailService emailService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.emailService = emailService;
    }

        //get appointment using appointment id
    @GetMapping(value="/{id}")
    public AppointmentDTO getAppointment(@PathVariable("id") Long id) throws NotFoundException
    {
        Appointment appointmentDb = appointmentService.getAppointmentById(id);
        if(appointmentDb == null)
            throw new NotFoundException(String.format("Appointment with id = %s was not found.",id));
        return AppointmentMapper.MAPPER.toAppointmentDTO(appointmentDb);
    }

    //get all appointments for database
    @GetMapping
    public List<AppointmentDTO> getAllAppointments()
    {
        List<Appointment> appointmentListDb = appointmentService.getAppointments();
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointmentListDb);
    }

    //appointment using doctor id and patient id as filter
    @GetMapping(value = "/filter", params = {"patientid", "doctorid"})
    public List<AppointmentDTO> findAllByDoctorAndPatient(@RequestParam("doctorid") Long doctorid, @RequestParam("patientid") Long patientid) throws NotFoundException
    {
        Doctor doctorDb = doctorService.getDoctor(doctorid);
        if(doctorDb == null)
            throw new NotFoundException(String.format("Doctor with id = %s was not found.", doctorid));

        Patient patientDb = patientService.getPatient(patientid);
        if(patientDb == null)
            throw new NotFoundException(String.format("Patient with id = %s was not found.", patientid));
        List<Appointment> appointments = appointmentService.getAppointmentsDoctorAndPatient(doctorDb,patientDb);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //appointment using doctor id as filter
    @GetMapping(value = "/filter", params = "doctorid")
    public List<AppointmentDTO> findByDoctor(@RequestParam("doctorid") Long doctorid) throws NotFoundException
    {
        Doctor doctorDb = doctorService.getDoctor(doctorid);
        if(doctorDb == null)
            throw new NotFoundException(String.format("Doctor with id=%s was not found.", doctorid));

        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorDb);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //appointment using patient id as filter
    @GetMapping(value = "/filter", params = "patientid")
    public List<AppointmentDTO> findByPatient(@RequestParam("patientid") Long patientid) throws NotFoundException
    {
        Patient patientDb = patientService.getPatient(patientid);
        if(patientDb == null)
            throw new NotFoundException(String.format("Patient with id = %s was not found.",patientid));

        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientDb);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //future appointments using doctorid as filter
    @GetMapping(value = "/future_appointments/filter", params = {"doctorid"})
    public List<AppointmentDTO> findAllByDoctorAndEndTimeGreaterThan(@RequestParam("doctorid") Long doctorid) throws NotFoundException
    {
        Doctor doctorDb = doctorService.getDoctor(doctorid);
        Date current_date = new Date();
        if(doctorDb == null)
            throw new NotFoundException((String.format("Doctor with id = %s was not found.", doctorid)));
        List<Appointment> appointments = appointmentService.findAllByDoctorAndEndTimeGreaterThan(doctorDb, current_date);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //future appointments
    @GetMapping(value = "/future_appointments")
    public List<AppointmentDTO> getFutureAppointments()
    {
        Date current_date = new Date();
        List<Appointment> appointments = appointmentService.findAllByEndTimeGreaterThan(current_date);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //appointments using took place as filter
    @GetMapping(value = "/filter", params = {"took-place"})
    public List<AppointmentDTO> findByTookPlace(@RequestParam("took-place") boolean tookPlace)
    {
        List<Appointment> appointments  = appointmentService.findAllByTookPlace(tookPlace);
        return AppointmentMapper.MAPPER.toAppointmentsDTO(appointments);
    }

    //Appointment post
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AppointmentDTO postAppointment(@RequestBody AppointmentDTO appointmentDTO) throws  NotFoundException, BadRequestException
    {
        Doctor doctorDb = doctorService.getDoctor(appointmentDTO.getDoctor_id());
        Patient patientDb = patientService.getPatient(appointmentDTO.getPatient_id());
        if(doctorDb == null)
            throw new NotFoundException(String.format("Doctor with id=%s was not found.", appointmentDTO.getDoctor_id()));
        if(patientDb == null)
            throw new NotFoundException(String.format("Patient with id=%s was not found.",appointmentDTO.getPatient_id()));
        if(appointmentDTO.getStartTime().after(appointmentDTO.getEndTime()))
            throw new BadRequestException(String.format("Start date %s is after end date %s", appointmentDTO.getStartTime(),appointmentDTO.getEndTime()));
        Integer count = appointmentService.countAllByStartTimeBetweenAndDoctorOrStartOrPatient(appointmentDTO.getStartTime(),
                appointmentDTO.getEndTime(), doctorDb,patientDb );
        if(count > 0)
        {
            throw new BadRequestException(String.format("Interval is already booked %s - %s",appointmentDTO.getStartTime(),appointmentDTO.getEndTime()));
        }

        Appointment appointment = AppointmentMapper.MAPPER.toAppointment(doctorDb, patientDb, appointmentDTO);
        appointment = appointmentService.save(appointment);

        EmailGood email = emailService.getEmail(doctorDb, "Appointments",String.format("You can see your appointment at http://localhost:8080/appointments/%s",appointment.getId()));
        return AppointmentMapper.MAPPER.toAppointmentDTO(appointment);
    }

    //canceled an appointment
    @PutMapping(value = "/cancel_appointment")
    public AppointmentDTO putAppointment(@RequestBody AppointmentDTO appointmentDTO) throws NotFoundException, BadRequestException
    {
        Appointment appointmentDb = appointmentService.getAppointmentById(appointmentDTO.getApp_id());
        Doctor doctorDb = doctorService.getDoctor(appointmentDTO.getDoctor_id());
        Patient patientDb = patientService.getPatient(appointmentDTO.getPatient_id());
        if(appointmentDb == null)
            throw new NotFoundException(String.format("Doctor with id=%s was not found.",appointmentDb.getId()));
        if(doctorDb == null)
            throw new NotFoundException(String.format("Doctor with id=%s was not found.", appointmentDTO.getDoctor_id()));
        if(patientDb == null)
            throw new NotFoundException(String.format("Patient with id=%s was not found.",appointmentDTO.getPatient_id()));
        if(appointmentDb.getTookPlace())
            throw new BadRequestException(String.format("Appointment already took place %s", appointmentDTO.getApp_id()));
        Date current_date = new Date();
        Date future_date = new Date(current_date.getTime() + 3600000); // hour in milliseconds

        if(appointmentDb.getStartTime().before(future_date) && appointmentDb.getStartTime().after(current_date))
            throw new BadRequestException(String.format("Appointments that occur in the next hour can't be canceled %s",
                    appointmentDTO.getApp_id()));

        AppointmentMapper.MAPPER.toAppointmentDTO(appointmentDb);
        appointmentDb = appointmentService.save(appointmentDb);
        return  AppointmentMapper.MAPPER.toAppointmentDTO(appointmentDb);
    }
}
