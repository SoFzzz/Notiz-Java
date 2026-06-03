package com.notiz.controller;

import com.notiz.dto.grademodification.GradeModificationRequestDTO;
import com.notiz.dto.grademodification.GradeModificationRequestResponseDTO;
import com.notiz.model.enums.ModificationRequestStatus;
import com.notiz.service.GradeModificationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-modification-requests")
@RequiredArgsConstructor
public class GradeModificationRequestController {

    private final GradeModificationRequestService gradeModificationRequestService;

    @PostMapping
    public ResponseEntity<GradeModificationRequestResponseDTO> requestModification(@RequestBody GradeModificationRequestDTO dto, @RequestHeader("X-User-ID") String professorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.requestModification(dto, professorId, tenantId));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<GradeModificationRequestResponseDTO> approveRequest(@PathVariable String id, @RequestHeader("X-User-ID") String adminId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.approveRequest(id, adminId, tenantId));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<GradeModificationRequestResponseDTO> rejectRequest(@PathVariable String id, @RequestHeader("X-User-ID") String adminId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.rejectRequest(id, adminId, tenantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeModificationRequestResponseDTO> getRequestById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.getRequestById(id, tenantId));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<GradeModificationRequestResponseDTO>> getProfessorRequests(@PathVariable String professorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.getProfessorRequests(professorId, tenantId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<GradeModificationRequestResponseDTO>> getRequestsByStatus(@PathVariable ModificationRequestStatus status, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeModificationRequestService.getRequestsByStatus(status, tenantId));
    }
}
