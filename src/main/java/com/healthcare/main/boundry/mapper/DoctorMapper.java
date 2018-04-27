package com.healthcare.main.boundry.mapper;

import com.healthcare.main.boundry.dto.DoctorDTO;
import com.healthcare.main.entity.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DoctorMapper {

    DoctorMapper MAPPER = Mappers.getMapper( DoctorMapper.class );

    @Mappings({
            @Mapping(source = "id", target = "doctor_id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phoneNumber.phoneNumber", target = "phoneNumber.PhoneNumber"),
            @Mapping(source = "function", target = "function"),
            @Mapping(source = "address.street", target = "address.street"),
            @Mapping(source = "email.email", target = "email.email"),
    })
    DoctorDTO toDoctorDTO(Doctor doctor);
    List<DoctorDTO> toDoctorsDTO(List<Doctor> doctors);

    @Mappings({
            @Mapping(source = "doctor_id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phoneNumber.phoneNumber", target = "phoneNumber.PhoneNumber"),
            @Mapping(source = "function", target = "function"),
            @Mapping(source = "address.street", target = "address.street"),
            @Mapping(source = "email.email", target = "email.email"),
    })
    Doctor toDoctor(DoctorDTO doctorDTO);

    @Mappings({
            @Mapping(source = "doctor_id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phoneNumber.phoneNumber", target = "phoneNumber.PhoneNumber"),
            @Mapping(source = "function", target = "function"),
            @Mapping(source = "address.street", target = "address.street"),
            @Mapping(source = "email.email", target = "email.email"),
    })
  void toDoctor(DoctorDTO doctorDTO, @MappingTarget Doctor doctorEntity);
}
