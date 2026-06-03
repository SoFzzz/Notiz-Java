package com.notiz.service.impl;

import com.notiz.dto.grade.BulkUploadResultDTO;
import com.notiz.dto.grade.GradeCreateDTO;
import com.notiz.dto.grade.GradeResponseDTO;
import com.notiz.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    @Override
    public GradeResponseDTO addGrade(GradeCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public GradeResponseDTO updateGrade(String id, double value, String tenantId) {
        return null;
    }

    @Override
    public void deleteGrade(String id, String tenantId) {
    }

    @Override
    public List<GradeResponseDTO> getStudentGrades(String studentId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<GradeResponseDTO> getSubjectGrades(String subjectId, String periodId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public GradeResponseDTO registerRecoveryMark(String id, double recoveryMark, String tenantId) {
        return null;
    }

    @Override
    public BulkUploadResultDTO bulkUploadGrades(MultipartFile file, String subjectId, String periodId, String tenantId) {
        return null;
    }
}
