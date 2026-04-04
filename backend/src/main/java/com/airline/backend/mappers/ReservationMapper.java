package com.airline.backend.mappers;

import com.airline.backend.dtos.request.PassengerDTO;
import com.airline.backend.dtos.request.ReservationRequestDTO;
import com.airline.backend.dtos.response.ReservationResponseDTO;
import com.airline.backend.models.entities.PassengerFlight;
import com.airline.backend.models.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mapping(source = "flight.flightId", target = "flightId")
    @Mapping(target = "passengers", ignore = true)
    ReservationResponseDTO toResponseDTO(Reservation reservation);

    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "flight", ignore = true)
    Reservation toEntity(ReservationRequestDTO dto);

    @Mapping(target = "passengerFlightId", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    PassengerFlight passengerDTOToEntity(PassengerDTO dto);

    PassengerDTO passengerEntityToDTO(PassengerFlight entity);
}
