package com.notiz.dto.grademodification;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeModificationRequestDTO {

    @NotBlank
    private String gradeId;

    @NotBlank
    @Size(min = 10, message = "Reason must be at least 10 characters long")
    private String reason;

    @Min(0)
    @Max(10)
    private double requestedValue;

}
