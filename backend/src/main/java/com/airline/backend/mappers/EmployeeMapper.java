package com.airline.backend.mappers;

import com.airline.backend.dtos.request.EmployeeRequestDTO;
import com.airline.backend.dtos.response.EmployeeResponseDTO;
import com.airline.backend.models.entities.InternalEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(source = "role.roleName", target = "roleName")
    EmployeeResponseDTO toResponseDTO(InternalEmployee employee);

    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "role", ignore = true)
    InternalEmployee toEntity(EmployeeRequestDTO dto);
}
