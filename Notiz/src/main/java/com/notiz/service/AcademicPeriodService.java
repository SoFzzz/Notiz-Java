package com.notiz.service;

import com.notiz.dto.period.AcademicPeriodCreateDTO;
import com.notiz.dto.period.AcademicPeriodResponseDTO;
import com.notiz.dto.period.AcademicPeriodUpdateDTO;
import com.notiz.model.enums.PeriodStatus;

import java.util.List;

public interface AcademicPeriodService {
    AcademicPeriodResponseDTO createPeriod(AcademicPeriodCreateDTO dto, String tenantId);
    AcademicPeriodResponseDTO updatePeriod(String id, AcademicPeriodUpdateDTO dto, String tenantId);
    void deletePeriod(String id, String tenantId);
    AcademicPeriodResponseDTO getPeriodById(String id, String tenantId);
    List<AcademicPeriodResponseDTO> getAllPeriods(String tenantId);
    List<AcademicPeriodResponseDTO> getPeriodsByStatus(PeriodStatus status, String tenantId);
    AcademicPeriodResponseDTO getActivePeriod(String tenantId);
}
