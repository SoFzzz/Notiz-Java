package com.notiz.service;

import com.notiz.dto.schedule.ScheduleCreateDTO;
import com.notiz.dto.schedule.ScheduleResponseDTO;
import com.notiz.dto.schedule.ScheduleUpdateDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDTO createSchedule(ScheduleCreateDTO dto, String tenantId);
    ScheduleResponseDTO updateSchedule(String id, ScheduleUpdateDTO dto, String tenantId);
    void deleteSchedule(String id, String tenantId);
    ScheduleResponseDTO getScheduleById(String id, String tenantId);
    List<ScheduleResponseDTO> getPeriodSchedules(String periodId, String tenantId);
    List<ScheduleResponseDTO> getSubjectSchedules(String subjectId, String tenantId);
    List<ScheduleResponseDTO> getStudentSchedules(String studentId, String periodId, String tenantId);
}
