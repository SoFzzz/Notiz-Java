package com.notiz.model;

import com.notiz.model.enums.PeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "academic_periods")
public class AcademicPeriod extends BaseDocument {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private PeriodStatus status;

    public boolean isActive() {
        return status == PeriodStatus.ACTIVE;
    }

    public boolean overlapsWith(AcademicPeriod other) {
        return (this.startDate.isBefore(other.endDate) || this.startDate.isEqual(other.endDate)) &&
               (other.startDate.isBefore(this.endDate) || other.startDate.isEqual(this.endDate));
    }

    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
}
