package com.arun.financebackend.controller;

import com.arun.financebackend.dto.DashboardSummaryResponse;
import com.arun.financebackend.service.DashboardService;
import com.arun.financebackend.util.RoleAccessUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * DashboardController — provides the financial summary dashboard.
 * Accessible by ALL roles: ADMIN, ANALYST, VIEWER.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // GET /api/dashboard/summary — Get financial summary (ALL roles)
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary(
            @RequestHeader("X-USER-ROLE") String roleHeader) {

        // All roles can access this — we just validate the role is a known value
        RoleAccessUtil.requireAnyRole(roleHeader);
        DashboardSummaryResponse summary = dashboardService.getSummary();
        return ResponseEntity.ok(summary);
    }
}
