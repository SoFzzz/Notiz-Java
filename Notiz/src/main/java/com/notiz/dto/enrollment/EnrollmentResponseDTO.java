package com.notiz.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponseDTO {

    private String id;
    private String studentId;
    private String studentName;
    private List<String> subjectIds;
    private List<String> subjectNames;
    private String periodId;
    private String periodName;
    private LocalDate enrollmentDate;

}
