package com.healthcare.main.boundry.controller;

import com.healthcare.main.boundry.dto.PatientDTO;
import com.healthcare.main.boundry.exception.BadRequestException;
import com.healthcare.main.boundry.exception.NotFoundException;
import com.healthcare.main.boundry.mapper.PatientMapper;
import com.healthcare.main.control.service.service.EmailService;
import com.healthcare.main.control.service.service.PatientService;
import com.healthcare.main.entity.model.Patient;
import com.healthcare.main.util.EmailGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "patients")
public class PatientController {
    PatientService patientService;
    EmailService emailService;

    @Autowired
   public PatientController(PatientService patientService, EmailService emailService)
   {
        this.patientService = patientService;
        this.emailService = emailService;
   }

   @GetMapping(value = "/{id}")
    public PatientDTO getPatient(@PathVariable("id") Long id) throws NotFoundException
   {
       Patient patient = patientService.getPatient(id);
       if(patient == null)
           throw new NotFoundException(String.format("Patient with id = % was not found.", id));
       return PatientMapper.MAPPER.toPatientDTO(patient);
   }


   @GetMapping
    public List<PatientDTO> getPatients() throws NotFoundException
   {
       List<Patient> patientsListDb = patientService.getAllPatients();
       if(patientsListDb.size() == 0)
           throw new NotFoundException(("There are no patients in datebase"));
       return PatientMapper.MAPPER.toPatientsDTO(patientsListDb);
   }

   @PostMapping
   @ResponseStatus(value = HttpStatus.CREATED)
    public PatientDTO savePatient(@RequestBody PatientDTO patientDTO)
   {
       Patient patient = PatientMapper.MAPPER.toPatient(patientDTO);
       patient = patientService.savePatient(patient);

       EmailGood email = emailService.getEmail(patient,"Acoount created",
               String.format("You can see your appointment at http://localhost;8080/patients/%s",patient.getId()));
       emailService.sendEmailHttp(email);
       return PatientMapper.MAPPER.toPatientDTO(patient);
   }

   @PutMapping(value="/{id}")
    public PatientDTO updatePatient(@PathVariable("id") Long id, @RequestBody PatientDTO patientDTO) throws BadRequestException, NotFoundException
    {
        if(!id.equals(patientDTO.getId()))
            throw new BadRequestException("The id is not the same with id from object");
        Patient patientDb = patientService.getPatient(id);
        if(patientDb == null)
            throw new NotFoundException(String.format("Patient with id=%s was not found.", id));
        PatientMapper.MAPPER.toPatient(patientDTO,patientDb);
        patientDb = patientService.updatePatient(patientDb);
        return PatientMapper.MAPPER.toPatientDTO(patientDb);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) throws NotFoundException
    {
        Patient patient = patientService.getPatient(id);
        if(patient == null)
            throw new NotFoundException(String.format("Patient with id=%s was not found.", id));
        patientService.deletePatient(patient);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllPatients()
    {
        patientService.deleteAllPatients();
    }
}
