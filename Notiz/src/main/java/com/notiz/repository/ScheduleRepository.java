package com.notiz.repository;

import com.notiz.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    List<Schedule> findByPeriodIdAndTenantId(String periodId, String tenantId);

    List<Schedule> findBySubjectIdAndTenantId(String subjectId, String tenantId);

    boolean existsByClassroomAndDayOfWeekAndStartTimeAndTenantId(String classroom, DayOfWeek dayOfWeek, LocalTime startTime, String tenantId);
}
