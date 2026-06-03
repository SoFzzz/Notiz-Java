package com.notiz.service;

import com.notiz.dto.audit.AuditLogResponseDTO;
import com.notiz.dto.audit.AuditReportDTO;
import com.notiz.model.enums.AuditAction;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {
    void logAction(String userId, String ipAddress, AuditAction action, String resource, String resourceId, String tenantId);
    List<AuditLogResponseDTO> getUserLogs(String userId, String tenantId);
    List<AuditLogResponseDTO> getLogsByDateRange(LocalDateTime start, LocalDateTime end, String tenantId);
    AuditReportDTO generateAuditReport(LocalDateTime start, LocalDateTime end, String tenantId);
}
