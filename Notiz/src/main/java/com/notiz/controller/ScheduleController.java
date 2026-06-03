package com.notiz.controller;

import com.notiz.dto.schedule.ScheduleCreateDTO;
import com.notiz.dto.schedule.ScheduleResponseDTO;
import com.notiz.dto.schedule.ScheduleUpdateDTO;
import com.notiz.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody ScheduleCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable String id, @RequestBody ScheduleUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        scheduleService.deleteSchedule(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id, tenantId));
    }

    @GetMapping("/period/{periodId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getPeriodSchedules(@PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.getPeriodSchedules(periodId, tenantId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getSubjectSchedules(@PathVariable String subjectId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.getSubjectSchedules(subjectId, tenantId));
    }

    @GetMapping("/student/{studentId}/period/{periodId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getStudentSchedules(@PathVariable String studentId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(scheduleService.getStudentSchedules(studentId, periodId, tenantId));
    }
}
