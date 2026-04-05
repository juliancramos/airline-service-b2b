package com.airline.backend.services;

import com.airline.backend.dtos.response.DashboardMetricsDTO;

public interface DashboardService {

    DashboardMetricsDTO getMetrics();
}
