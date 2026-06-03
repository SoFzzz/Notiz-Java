package com.notiz.service;

import com.notiz.dto.grade.BulkUploadResultDTO;
import com.notiz.dto.grade.GradeCreateDTO;
import com.notiz.dto.grade.GradeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GradeService {
    GradeResponseDTO addGrade(GradeCreateDTO dto, String tenantId);
    GradeResponseDTO updateGrade(String id, double value, String tenantId);
    void deleteGrade(String id, String tenantId);
    List<GradeResponseDTO> getStudentGrades(String studentId, String tenantId);
    List<GradeResponseDTO> getSubjectGrades(String subjectId, String periodId, String tenantId);
    GradeResponseDTO registerRecoveryMark(String id, double recoveryMark, String tenantId);
    BulkUploadResultDTO bulkUploadGrades(MultipartFile file, String subjectId, String periodId, String tenantId);
}
