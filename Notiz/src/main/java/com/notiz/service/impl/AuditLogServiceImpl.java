package com.notiz.service.impl;

import com.notiz.dto.audit.AuditLogResponseDTO;
import com.notiz.dto.audit.AuditReportDTO;
import com.notiz.model.enums.AuditAction;
import com.notiz.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    @Override
    public void logAction(String userId, String ipAddress, AuditAction action, String resource, String resourceId, String tenantId) {
    }

    @Override
    public List<AuditLogResponseDTO> getUserLogs(String userId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<AuditLogResponseDTO> getLogsByDateRange(LocalDateTime start, LocalDateTime end, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public AuditReportDTO generateAuditReport(LocalDateTime start, LocalDateTime end, String tenantId) {
        return null;
    }
}
