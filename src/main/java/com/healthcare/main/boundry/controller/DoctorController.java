package com.healthcare.main.boundry.controller;

import com.healthcare.main.boundry.dto.DoctorDTO;
import com.healthcare.main.boundry.exception.BadRequestException;
import com.healthcare.main.boundry.exception.NotFoundException;
import com.healthcare.main.boundry.mapper.DoctorMapper;
import com.healthcare.main.control.service.service.DoctorService;
import com.healthcare.main.control.service.service.EmailService;
import com.healthcare.main.entity.model.Doctor;
import com.healthcare.main.util.EmailGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "doctors")
public class DoctorController {

    private DoctorService doctorService;
    private EmailService emailService;

    @Autowired
    public DoctorController(DoctorService doctorService, EmailService emailService)
    {
        this.doctorService = doctorService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/{id}")
    public DoctorDTO getDoctor(@PathVariable("id") Long id) throws NotFoundException
    {
        Doctor doctor = doctorService.getDoctor(id);
        if(doctor == null)
            throw  new NotFoundException(String.format("Doctor with id = %s was not found.", id));
        return DoctorMapper.MAPPER.toDoctorDTO(doctor);
    }
    @GetMapping
    public List<DoctorDTO> getDoctors() throws NotFoundException
    {
        List<Doctor> doctorListDb = doctorService.getAllDoctors();
        if(doctorListDb.size() == 0)
            throw  new NotFoundException(" There are no doctors in database!");
        return DoctorMapper.MAPPER.toDoctorsDTO(doctorListDb);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DoctorDTO saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = DoctorMapper.MAPPER.toDoctor(doctorDTO);
        doctor = doctorService.saveDoctor(doctor);
        EmailGood email = emailService.getEmail(doctor,"Your account has been creted",
                String.format("You can see your appointment at http://localhost:8080/doctors/%s",doctor.getId()));
        emailService.sendEmailHttp(email);
        return DoctorMapper.MAPPER.toDoctorDTO(doctor);
    }


    @PutMapping(value = "/{id}")
    public DoctorDTO updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorDTO doctorDTO) throws BadRequestException, NotFoundException {
        //validate request
        if (!id.equals(doctorDTO.getId())) {
            throw new BadRequestException("The id is not the same with id from object");
        }
        Doctor doctorDb = doctorService.getDoctor(id);
        if (doctorDb == null) {
            throw new NotFoundException(String.format("Doctor with id=%s was not found.", id));
        }
        DoctorMapper.MAPPER.toDoctor(doctorDTO,doctorDb);
        doctorDb = doctorService.updateDoctor(doctorDb);
        return DoctorMapper.MAPPER.toDoctorDTO(doctorDb);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) throws NotFoundException {
        Doctor doctorDb = doctorService.getDoctor(id);
        if (doctorDb == null) {
            throw new NotFoundException(String.format("Doctor with id=%s was not found.", id));
        }
        doctorService.deleteDoctor(doctorDb);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllDoctor()
    {
       doctorService.deleteAllDoctors();
    }
}