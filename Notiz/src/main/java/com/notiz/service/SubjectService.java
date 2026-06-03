package com.notiz.service;

import com.notiz.dto.subject.EvaluationCriterionDTO;
import com.notiz.dto.subject.SubjectCreateDTO;
import com.notiz.dto.subject.SubjectResponseDTO;
import com.notiz.dto.subject.SubjectUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    SubjectResponseDTO createSubject(SubjectCreateDTO dto, String tenantId);
    SubjectResponseDTO updateSubject(String id, SubjectUpdateDTO dto, String tenantId);
    void deleteSubject(String id, String tenantId);
    SubjectResponseDTO getSubjectById(String id, String tenantId);
    Page<SubjectResponseDTO> getAllSubjects(String tenantId, Pageable pageable);
    List<SubjectResponseDTO> getProfessorSubjects(String professorId, String tenantId);
    SubjectResponseDTO addEvaluationCriterion(String id, EvaluationCriterionDTO dto, String tenantId);
}
