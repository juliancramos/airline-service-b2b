package com.airline.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricsDTO {
    private long totalScheduledFlights;
    private long totalConfirmedReservations;
    private long flightsNearingCapacity;
}
