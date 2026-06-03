package com.notiz.dto.subject;

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
public class SubjectResponseDTO {

    private String id;
    private String name;
    private String code;
    private String professorId;
    private String professorName;
    private List<EvaluationCriterionDTO> evaluationCriteria;
    private LocalDateTime createdAt;

}
