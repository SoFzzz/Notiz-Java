package com.notiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grades")
public class Grade extends BaseDocument implements Auditable, Exportable {

    private String studentId;
    private String subjectId;
    private String periodId;
    private String criterionName;
    private double value;
    private String feedback;
    private Double recoveryMark;
    private String createdBy;

    public double getEffectiveValue() {
        return recoveryMark != null ? recoveryMark : value;
    }

    public boolean hasRecoveryMark() {
        return recoveryMark != null;
    }

    public boolean hasFeedback() {
        return feedback != null && !feedback.trim().isEmpty();
    }

    // --- Auditable Implementation ---
    @Override
    public String getAuditableId() {
        return getId();
    }

    @Override
    public String getAuditableType() {
        return "Grade";
    }

    @Override
    public String getAuditDescription() {
        return "Grade for student " + studentId + " in subject " + subjectId + ": " + getEffectiveValue();
    }

    // --- Exportable Implementation ---
    @Override
    public List<Object> toExportRow() {
        List<Object> row = new ArrayList<>();
        row.add(id);
        row.add(studentId);
        row.add(subjectId);
        row.add(periodId);
        row.add(criterionName);
        row.add(value);
        row.add(recoveryMark != null ? recoveryMark : "");
        row.add(getEffectiveValue());
        return row;
    }

    @Override
    public List<String> getExportHeaders() {
        return List.of("ID", "Student ID", "Subject ID", "Period ID", "Criterion", "Original Value", "Recovery Mark", "Effective Value");
    }

    @Override
    public String getExportFileName() {
        return "grades_export_" + LocalDateTime.now() + ".csv";
    }
}
