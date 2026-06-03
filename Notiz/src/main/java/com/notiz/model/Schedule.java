package com.notiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schedules")
public class Schedule extends BaseDocument {

    private String subjectId;
    private String classroom;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String periodId;

    public long getDurationInMinutes() {
        if (startTime != null && endTime != null) {
            return ChronoUnit.MINUTES.between(startTime, endTime);
        }
        return 0;
    }

    public boolean overlapsWith(Schedule other) {
        if (this.dayOfWeek != other.dayOfWeek) {
            return false;
        }
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }
}
