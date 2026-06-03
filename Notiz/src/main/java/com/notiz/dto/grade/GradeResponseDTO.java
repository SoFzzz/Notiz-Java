package com.notiz.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponseDTO {

    private String id;
    private String studentId;
    private String studentName;
    private String subjectId;
    private String subjectName;
    private String periodId;
    private String criterionName;
    private double value;
    private String feedback;
    private Double recoveryMark;
    private double effectiveValue;
    private String createdBy;
    private LocalDateTime createdAt;

}
