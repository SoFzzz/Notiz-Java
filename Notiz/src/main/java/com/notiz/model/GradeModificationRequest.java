package com.notiz.model;

import com.notiz.model.enums.ModificationRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grade_modification_requests")
public class GradeModificationRequest extends BaseDocument implements Auditable {

    private String gradeId;
    private String professorId;
    private String adminId;
    private String reason;
    private double originalValue;
    private double requestedValue;
    private ModificationRequestStatus status = ModificationRequestStatus.PENDING;
    private LocalDateTime resolvedAt;

    public void approve(String adminId) {
        this.status = ModificationRequestStatus.APPROVED;
        this.adminId = adminId;
        this.resolvedAt = LocalDateTime.now();
    }

    public void reject(String adminId) {
        this.status = ModificationRequestStatus.REJECTED;
        this.adminId = adminId;
        this.resolvedAt = LocalDateTime.now();
    }

    public boolean isPending() {
        return this.status == ModificationRequestStatus.PENDING;
    }

    public double getValueDifference() {
        return requestedValue - originalValue;
    }

    // --- Auditable Implementation ---
    @Override
    public String getAuditableId() {
        return getId();
    }

    @Override
    public String getAuditableType() {
        return "GradeModificationRequest";
    }

    @Override
    public String getAuditDescription() {
        return "Modification request for grade " + gradeId + " to value " + requestedValue;
    }
}
