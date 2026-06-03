package com.notiz.service;

import com.notiz.dto.grademodification.GradeModificationRequestDTO;
import com.notiz.dto.grademodification.GradeModificationRequestResponseDTO;
import com.notiz.model.enums.ModificationRequestStatus;

import java.util.List;

public interface GradeModificationRequestService {
    GradeModificationRequestResponseDTO requestModification(GradeModificationRequestDTO dto, String professorId, String tenantId);
    GradeModificationRequestResponseDTO approveRequest(String id, String adminId, String tenantId);
    GradeModificationRequestResponseDTO rejectRequest(String id, String adminId, String tenantId);
    GradeModificationRequestResponseDTO getRequestById(String id, String tenantId);
    List<GradeModificationRequestResponseDTO> getProfessorRequests(String professorId, String tenantId);
    List<GradeModificationRequestResponseDTO> getRequestsByStatus(ModificationRequestStatus status, String tenantId);
}
