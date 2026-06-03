package com.notiz.dto.period;

import com.notiz.model.enums.PeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicPeriodUpdateDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private PeriodStatus status;

}
