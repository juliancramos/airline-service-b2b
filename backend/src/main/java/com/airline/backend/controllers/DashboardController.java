package com.airline.backend.controllers;

import com.airline.backend.dtos.response.DashboardMetricsDTO;
import com.airline.backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/metrics")
    public ResponseEntity<DashboardMetricsDTO> getMetrics() {
        return ResponseEntity.ok(dashboardService.getMetrics());
    }
}
