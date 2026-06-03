package com.notiz.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreateDTO {

    @NotBlank
    private String studentId;

    @NotEmpty
    private List<String> subjectIds;

    @NotBlank
    private String periodId;

}
