package com.notiz.service.impl;

import com.notiz.dto.period.AcademicPeriodCreateDTO;
import com.notiz.dto.period.AcademicPeriodResponseDTO;
import com.notiz.dto.period.AcademicPeriodUpdateDTO;
import com.notiz.model.enums.PeriodStatus;
import com.notiz.service.AcademicPeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicPeriodServiceImpl implements AcademicPeriodService {

    @Override
    public AcademicPeriodResponseDTO createPeriod(AcademicPeriodCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public AcademicPeriodResponseDTO updatePeriod(String id, AcademicPeriodUpdateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void deletePeriod(String id, String tenantId) {
    }

    @Override
    public AcademicPeriodResponseDTO getPeriodById(String id, String tenantId) {
        return null;
    }

    @Override
    public List<AcademicPeriodResponseDTO> getAllPeriods(String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<AcademicPeriodResponseDTO> getPeriodsByStatus(PeriodStatus status, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public AcademicPeriodResponseDTO getActivePeriod(String tenantId) {
        return null;
    }
}
