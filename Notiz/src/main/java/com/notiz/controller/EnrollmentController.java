package com.notiz.controller;

import com.notiz.dto.enrollment.EnrollmentCreateDTO;
import com.notiz.dto.enrollment.EnrollmentResponseDTO;
import com.notiz.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(@RequestBody EnrollmentCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(dto, tenantId));
    }

    @DeleteMapping("/student/{studentId}/period/{periodId}")
    public ResponseEntity<Void> unenrollStudent(@PathVariable String studentId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        enrollmentService.unenrollStudent(studentId, periodId, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id, tenantId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getStudentEnrollments(@PathVariable String studentId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(studentId, tenantId));
    }

    @GetMapping("/period/{periodId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getPeriodEnrollments(@PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(enrollmentService.getPeriodEnrollments(periodId, tenantId));
    }

    @PostMapping("/{id}/subjects/{subjectId}")
    public ResponseEntity<EnrollmentResponseDTO> addSubjectToEnrollment(@PathVariable String id, @PathVariable String subjectId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(enrollmentService.addSubjectToEnrollment(id, subjectId, tenantId));
    }
}
