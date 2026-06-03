package com.notiz.model;

import com.notiz.model.embedded.EvaluationCriterion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subjects")
public class Subject extends BaseDocument implements Auditable {

    private String name;
    private String code;
    private String professorId;
    private List<EvaluationCriterion> evaluationCriteria = new ArrayList<>();

    public void addCriterion(EvaluationCriterion criterion) {
        evaluationCriteria.add(criterion);
    }

    public void removeCriterion(String name) {
        evaluationCriteria.removeIf(c -> c.getName().equals(name));
    }

    public boolean validateCriteriaWeights() {
        return getTotalWeight() == 100.0;
    }

    public double getTotalWeight() {
        return evaluationCriteria.stream()
                .mapToDouble(EvaluationCriterion::getWeight)
                .sum();
    }

    // --- Auditable Implementation ---
    @Override
    public String getAuditableId() {
        return getId();
    }

    @Override
    public String getAuditableType() {
        return "Subject";
    }

    @Override
    public String getAuditDescription() {
        return "Subject: " + name + " (" + code + ")";
    }
}
