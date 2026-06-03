package com.notiz.service.impl;

import com.notiz.dto.enrollment.EnrollmentCreateDTO;
import com.notiz.dto.enrollment.EnrollmentResponseDTO;
import com.notiz.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void unenrollStudent(String studentId, String periodId, String tenantId) {
    }

    @Override
    public EnrollmentResponseDTO getEnrollmentById(String id, String tenantId) {
        return null;
    }

    @Override
    public List<EnrollmentResponseDTO> getStudentEnrollments(String studentId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<EnrollmentResponseDTO> getPeriodEnrollments(String periodId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public EnrollmentResponseDTO addSubjectToEnrollment(String id, String subjectId, String tenantId) {
        return null;
    }
}
