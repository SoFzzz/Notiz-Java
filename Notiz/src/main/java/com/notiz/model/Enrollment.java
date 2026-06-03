package com.notiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "enrollments")
public class Enrollment extends BaseDocument implements Exportable {

    private String studentId;
    private List<String> subjectIds = new ArrayList<>();
    private String periodId;
    private LocalDate enrollmentDate;

    public void addSubject(String subjectId) {
        if (!subjectIds.contains(subjectId)) {
            subjectIds.add(subjectId);
        }
    }

    public void removeSubject(String subjectId) {
        subjectIds.remove(subjectId);
    }

    public int getSubjectCount() {
        return subjectIds.size();
    }

    public boolean isEnrolledIn(String subjectId) {
        return subjectIds.contains(subjectId);
    }

    // --- Exportable Implementation ---
    @Override
    public List<Object> toExportRow() {
        List<Object> row = new ArrayList<>();
        row.add(id);
        row.add(studentId);
        row.add(periodId);
        row.add(enrollmentDate);
        row.add(String.join(", ", subjectIds));
        return row;
    }

    @Override
    public List<String> getExportHeaders() {
        return List.of("ID", "Student ID", "Period ID", "Enrollment Date", "Subjects");
    }

    @Override
    public String getExportFileName() {
        return "enrollments_export_" + LocalDateTime.now() + ".csv";
    }
}
