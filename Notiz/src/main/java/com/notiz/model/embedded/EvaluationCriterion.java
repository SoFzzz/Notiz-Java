package com.notiz.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCriterion {
    private String name;
    private double weight;
    private String description;

    @Override
    public String toString() {
        return "EvaluationCriterion{name='" + name + "', weight=" + weight + "}";
    }
}
