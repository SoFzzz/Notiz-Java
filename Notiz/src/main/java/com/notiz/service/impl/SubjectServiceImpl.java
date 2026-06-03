package com.notiz.service.impl;

import com.notiz.dto.subject.EvaluationCriterionDTO;
import com.notiz.dto.subject.SubjectCreateDTO;
import com.notiz.dto.subject.SubjectResponseDTO;
import com.notiz.dto.subject.SubjectUpdateDTO;
import com.notiz.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    @Override
    public SubjectResponseDTO createSubject(SubjectCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public SubjectResponseDTO updateSubject(String id, SubjectUpdateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void deleteSubject(String id, String tenantId) {
    }

    @Override
    public SubjectResponseDTO getSubjectById(String id, String tenantId) {
        return null;
    }

    @Override
    public Page<SubjectResponseDTO> getAllSubjects(String tenantId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public List<SubjectResponseDTO> getProfessorSubjects(String professorId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public SubjectResponseDTO addEvaluationCriterion(String id, EvaluationCriterionDTO dto, String tenantId) {
        return null;
    }
}
