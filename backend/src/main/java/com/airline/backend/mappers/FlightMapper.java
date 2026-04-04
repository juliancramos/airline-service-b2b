package com.airline.backend.mappers;

import com.airline.backend.dtos.request.FlightRequestDTO;
import com.airline.backend.dtos.response.FlightResponseDTO;
import com.airline.backend.models.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlightMapper {

    @Mapping(source = "createdBy.employeeId", target = "createdById")
    FlightResponseDTO toResponseDTO(Flight flight);

    @Mapping(target = "flightId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Flight toEntity(FlightRequestDTO dto);
}
