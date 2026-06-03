package com.notiz.dto.grademodification;

import com.notiz.model.enums.ModificationRequestStatus;
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
public class GradeModificationRequestResponseDTO {

    private String id;
    private String gradeId;
    private String professorId;
    private String professorName;
    private String adminId;
    private String reason;
    private double originalValue;
    private double requestedValue;
    private ModificationRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

}
