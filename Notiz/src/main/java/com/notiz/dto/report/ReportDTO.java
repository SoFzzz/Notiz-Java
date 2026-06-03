package com.notiz.dto.report;

import com.notiz.dto.grade.GradeResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {

    private String studentName;
    private String periodName;
    private List<GradeResponseDTO> grades;
    private double weightedAverage;
    private String qualitativeLabel;
    private LocalDateTime generatedAt;

}
