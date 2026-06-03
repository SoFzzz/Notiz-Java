package com.notiz.service;

import com.notiz.dto.enrollment.EnrollmentCreateDTO;
import com.notiz.dto.enrollment.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDTO enrollStudent(EnrollmentCreateDTO dto, String tenantId);
    void unenrollStudent(String studentId, String periodId, String tenantId);
    EnrollmentResponseDTO getEnrollmentById(String id, String tenantId);
    List<EnrollmentResponseDTO> getStudentEnrollments(String studentId, String tenantId);
    List<EnrollmentResponseDTO> getPeriodEnrollments(String periodId, String tenantId);
    EnrollmentResponseDTO addSubjectToEnrollment(String id, String subjectId, String tenantId);
}
