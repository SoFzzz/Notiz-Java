package com.notiz.controller;

import com.notiz.dto.gradescale.GradeRangeDTO;
import com.notiz.dto.gradescale.GradeScaleCreateDTO;
import com.notiz.dto.gradescale.GradeScaleResponseDTO;
import com.notiz.dto.gradescale.GradeScaleUpdateDTO;
import com.notiz.service.GradeScaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-scales")
@RequiredArgsConstructor
public class GradeScaleController {

    private final GradeScaleService gradeScaleService;

    @PostMapping
    public ResponseEntity<GradeScaleResponseDTO> createScale(@RequestBody GradeScaleCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.createScale(dto, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeScaleResponseDTO> updateScale(@PathVariable String id, @RequestBody GradeScaleUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.updateScale(id, dto, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScale(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        gradeScaleService.deleteScale(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeScaleResponseDTO> getScaleById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.getScaleById(id, tenantId));
    }

    @GetMapping
    public ResponseEntity<List<GradeScaleResponseDTO>> getAllScales(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.getAllScales(tenantId));
    }

    @PostMapping("/{id}/ranges")
    public ResponseEntity<GradeScaleResponseDTO> addRangeToScale(@PathVariable String id, @RequestBody GradeRangeDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.addRangeToScale(id, dto, tenantId));
    }

    @GetMapping("/{id}/label")
    public ResponseEntity<String> getQualitativeLabel(@PathVariable String id, @RequestParam double value, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gradeScaleService.getQualitativeLabel(value, id, tenantId));
    }
}
