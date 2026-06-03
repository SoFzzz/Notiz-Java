package com.notiz.controller;

import com.notiz.dto.period.AcademicPeriodCreateDTO;
import com.notiz.dto.period.AcademicPeriodResponseDTO;
import com.notiz.dto.period.AcademicPeriodUpdateDTO;
import com.notiz.model.enums.PeriodStatus;
import com.notiz.service.AcademicPeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periods")
@RequiredArgsConstructor
public class AcademicPeriodController {

    private final AcademicPeriodService academicPeriodService;

    @PostMapping
    public ResponseEntity<AcademicPeriodResponseDTO> createPeriod(@RequestBody AcademicPeriodCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.createPeriod(dto, tenantId));
    }

    @GetMapping
    public ResponseEntity<List<AcademicPeriodResponseDTO>> getAllPeriods(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.getAllPeriods(tenantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicPeriodResponseDTO> getPeriodById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.getPeriodById(id, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicPeriodResponseDTO> updatePeriod(@PathVariable String id, @RequestBody AcademicPeriodUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.updatePeriod(id, dto, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriod(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        academicPeriodService.deletePeriod(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AcademicPeriodResponseDTO>> getPeriodsByStatus(@PathVariable PeriodStatus status, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.getPeriodsByStatus(status, tenantId));
    }

    @GetMapping("/active")
    public ResponseEntity<AcademicPeriodResponseDTO> getActivePeriod(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(academicPeriodService.getActivePeriod(tenantId));
    }
}
