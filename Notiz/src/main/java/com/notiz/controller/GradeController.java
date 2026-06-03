package com.notiz.controller;

import com.notiz.dto.grade.BulkUploadResultDTO;
import com.notiz.dto.grade.GradeCreateDTO;
import com.notiz.dto.grade.GradeResponseDTO;
import com.notiz.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> addGrade(@RequestBody GradeCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.addGrade(dto, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(@PathVariable String id, @RequestParam double value, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.updateGrade(id, value, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        gradeService.deleteGrade(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getStudentGrades(@PathVariable String studentId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.getStudentGrades(studentId, tenantId));
    }

    @GetMapping("/subject/{subjectId}/period/{periodId}")
    public ResponseEntity<List<GradeResponseDTO>> getSubjectGrades(@PathVariable String subjectId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.getSubjectGrades(subjectId, periodId, tenantId));
    }

    @PostMapping("/{id}/recovery")
    public ResponseEntity<GradeResponseDTO> registerRecoveryMark(@PathVariable String id, @RequestParam double recoveryMark, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.registerRecoveryMark(id, recoveryMark, tenantId));
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResultDTO> bulkUploadGrades(@RequestParam("file") MultipartFile file, @RequestParam String subjectId, @RequestParam String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeService.bulkUploadGrades(file, subjectId, periodId, tenantId));
    }
}
