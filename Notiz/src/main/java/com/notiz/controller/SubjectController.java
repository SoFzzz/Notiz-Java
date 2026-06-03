package com.notiz.controller;

import com.notiz.dto.subject.EvaluationCriterionDTO;
import com.notiz.dto.subject.SubjectCreateDTO;
import com.notiz.dto.subject.SubjectResponseDTO;
import com.notiz.dto.subject.SubjectUpdateDTO;
import com.notiz.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody SubjectCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(subjectService.createSubject(dto, tenantId));
    }

    @GetMapping
    public ResponseEntity<Page<SubjectResponseDTO>> getAllSubjects(@RequestHeader("X-Tenant-ID") String tenantId, Pageable pageable) {
        return ResponseEntity.ok(subjectService.getAllSubjects(tenantId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(subjectService.getSubjectById(id, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@PathVariable String id, @RequestBody SubjectUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(subjectService.updateSubject(id, dto, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        subjectService.deleteSubject(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<SubjectResponseDTO>> getProfessorSubjects(@PathVariable String professorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(subjectService.getProfessorSubjects(professorId, tenantId));
    }

    @PostMapping("/{id}/criteria")
    public ResponseEntity<SubjectResponseDTO> addEvaluationCriterion(@PathVariable String id, @RequestBody EvaluationCriterionDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(subjectService.addEvaluationCriterion(id, dto, tenantId));
    }
}
