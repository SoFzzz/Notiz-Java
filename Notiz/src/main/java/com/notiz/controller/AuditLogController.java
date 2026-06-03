package com.notiz.controller;

import com.notiz.dto.audit.AuditLogResponseDTO;
import com.notiz.dto.audit.AuditReportDTO;
import com.notiz.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponseDTO>> getUserLogs(@PathVariable String userId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(auditLogService.getUserLogs(userId, tenantId));
    }

    @GetMapping("/range")
    public ResponseEntity<List<AuditLogResponseDTO>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(auditLogService.getLogsByDateRange(start, end, tenantId));
    }

    @GetMapping("/report")
    public ResponseEntity<AuditReportDTO> generateAuditReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(auditLogService.generateAuditReport(start, end, tenantId));
    }
}
