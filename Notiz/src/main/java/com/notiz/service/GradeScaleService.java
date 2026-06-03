package com.notiz.service;

import com.notiz.dto.gradescale.GradeRangeDTO;
import com.notiz.dto.gradescale.GradeScaleCreateDTO;
import com.notiz.dto.gradescale.GradeScaleResponseDTO;
import com.notiz.dto.gradescale.GradeScaleUpdateDTO;

import java.util.List;

public interface GradeScaleService {
    GradeScaleResponseDTO createScale(GradeScaleCreateDTO dto, String tenantId);
    GradeScaleResponseDTO updateScale(String id, GradeScaleUpdateDTO dto, String tenantId);
    void deleteScale(String id, String tenantId);
    GradeScaleResponseDTO getScaleById(String id, String tenantId);
    List<GradeScaleResponseDTO> getAllScales(String tenantId);
    GradeScaleResponseDTO addRangeToScale(String id, GradeRangeDTO dto, String tenantId);
    String getQualitativeLabel(double value, String scaleId, String tenantId);
}
