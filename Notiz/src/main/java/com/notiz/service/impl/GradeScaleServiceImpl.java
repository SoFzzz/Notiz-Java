package com.notiz.service.impl;

import com.notiz.dto.gradescale.GradeRangeDTO;
import com.notiz.dto.gradescale.GradeScaleCreateDTO;
import com.notiz.dto.gradescale.GradeScaleResponseDTO;
import com.notiz.dto.gradescale.GradeScaleUpdateDTO;
import com.notiz.service.GradeScaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeScaleServiceImpl implements GradeScaleService {

    @Override
    public GradeScaleResponseDTO createScale(GradeScaleCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public GradeScaleResponseDTO updateScale(String id, GradeScaleUpdateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void deleteScale(String id, String tenantId) {
    }

    @Override
    public GradeScaleResponseDTO getScaleById(String id, String tenantId) {
        return null;
    }

    @Override
    public List<GradeScaleResponseDTO> getAllScales(String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public GradeScaleResponseDTO addRangeToScale(String id, GradeRangeDTO dto, String tenantId) {
        return null;
    }

    @Override
    public String getQualitativeLabel(double value, String scaleId, String tenantId) {
        return null;
    }
}
