package com.notiz.dto.gradescale;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRangeDTO {

    @Min(0)
    private double minValue;

    @Max(10)
    private double maxValue;

    @NotBlank
    private String label;

    private String qualitativeDescription;

}
