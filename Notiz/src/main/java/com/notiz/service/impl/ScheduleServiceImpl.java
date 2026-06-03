package com.notiz.service.impl;

import com.notiz.dto.schedule.ScheduleCreateDTO;
import com.notiz.dto.schedule.ScheduleResponseDTO;
import com.notiz.dto.schedule.ScheduleUpdateDTO;
import com.notiz.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public ScheduleResponseDTO createSchedule(ScheduleCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public ScheduleResponseDTO updateSchedule(String id, ScheduleUpdateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void deleteSchedule(String id, String tenantId) {
    }

    @Override
    public ScheduleResponseDTO getScheduleById(String id, String tenantId) {
        return null;
    }

    @Override
    public List<ScheduleResponseDTO> getPeriodSchedules(String periodId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<ScheduleResponseDTO> getSubjectSchedules(String subjectId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public List<ScheduleResponseDTO> getStudentSchedules(String studentId, String periodId, String tenantId) {
        return Collections.emptyList();
    }
}
