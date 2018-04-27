package com.healthcare.main.boundry.mapper;

import com.healthcare.main.boundry.dto.AppointmentDTO;
import com.healthcare.main.entity.model.Appointment;
import com.healthcare.main.entity.model.Doctor;
import com.healthcare.main.entity.model.Patient;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface  AppointmentMapper {
    AppointmentMapper MAPPER = Mappers.getMapper(AppointmentMapper.class);

    @Mappings({
            @Mapping(source = "appointment.id", target = "app_id"),
            @Mapping(source = "appointment.doctor.id", target = "doctor_id"),
            @Mapping(source = "appointment.patient.id", target = "patient_id"),
            @Mapping(source = "appointment.startTime", target = "startTime"),
            @Mapping(source = "appointment.endTime", target = "endTime"),
            @Mapping(source = "appointment.cause", target = "cause"),
            @Mapping(source = "appointment.canceledAppointment.canceled", target = "cancel"),
    })
    AppointmentDTO toAppointmentDTO(Appointment appointment);
    List<AppointmentDTO> toAppointmentsDTO(List<Appointment> appointments);

    @Mappings({
            @Mapping(source = "appointmentDTO.app_id", target = "id"),
            @Mapping(source = "doctor", target = "doctor"),
            @Mapping(source = "patient", target = "patient"),
            @Mapping(source = "appointmentDTO.startTime", target = "startTime"),
            @Mapping(source = "appointmentDTO.endTime", target = "endTime"),
            @Mapping(source = "appointmentDTO.cause", target = "cause"),
            @Mapping(source = "appointmentDTO.cancel", target = "canceledAppointment.canceled"),
    })
    Appointment toAppointment(Doctor doctor, Patient patient, AppointmentDTO appointmentDTO);

    @Mappings({
            @Mapping(source = "appointmentDTO.app_id", target = "id"),
            @Mapping(source = "doctor", target = "doctor"),
            @Mapping(source = "patient", target = "patient"),
            @Mapping(source = "appointmentDTO.startTime", target = "startTime"),
            @Mapping(source = "appointmentDTO.endTime", target = "endTime"),
            @Mapping(source = "appointmentDTO.cause", target = "cause"),
            @Mapping(source = "appointmentDTO.cancel", target = "canceledAppointment.canceled"),
    })
    void toAppointment(Doctor doctor, Patient patient, AppointmentDTO appointmentDTO, @MappingTarget Appointment appointment);

}

