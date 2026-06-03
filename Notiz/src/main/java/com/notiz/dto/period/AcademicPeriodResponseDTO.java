package com.notiz.dto.period;

import com.notiz.model.enums.PeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicPeriodResponseDTO {

    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private PeriodStatus status;
    private boolean isActive;
    private long durationInDays;

}
