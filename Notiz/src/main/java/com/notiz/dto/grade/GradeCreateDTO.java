package com.notiz.dto.grade;

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
public class GradeCreateDTO {

    @NotBlank
    private String studentId;

    @NotBlank
    private String subjectId;

    @NotBlank
    private String periodId;

    @NotBlank
    private String criterionName;

    @Min(0)
    @Max(10)
    private double value;

    private String feedback;

}
