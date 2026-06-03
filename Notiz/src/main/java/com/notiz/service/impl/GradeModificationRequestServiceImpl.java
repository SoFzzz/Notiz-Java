package com.notiz.service.impl;

import com.notiz.dto.grademodification.GradeModificationRequestDTO;
import com.notiz.dto.grademodification.GradeModificationRequestResponseDTO;
import com.notiz.model.enums.ModificationRequestStatus;
import com.notiz.service.GradeModificationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeModificationRequestServiceImpl implements GradeModificationRequestService {

    @Override
    public GradeModificationRequestResponseDTO requestModification(GradeModificationRequestDTO dto, String professorId, String tenantId) {
        return null;
    }

    @Override
    public GradeModificationRequestResponseDTO approveRequest(String id, String adminId, String tenantId) {
        return null;
    }

    @Override
    public GradeModificationRequestResponseDTO rejectRequest(String id, String adminId, String tenantId) {
        return null;
    }

    @Override
    public GradeModificationRequestResponseDTO getRequestById(String id, String tenantId) {
        return null;
    }

    @Override
    public List<GradeModificationRequestResponseDTO> getProfessorRequests(String professorId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<GradeModificationRequestResponseDTO> getRequestsByStatus(ModificationRequestStatus status, String tenantId) {
        return Collections.emptyList();
    }
}
