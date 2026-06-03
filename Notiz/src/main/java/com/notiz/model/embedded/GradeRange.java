package com.notiz.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRange {
    private double minValue;
    private double maxValue;
    private String label;
    private String qualitativeDescription;

    public boolean containsValue(double value) {
        return value >= minValue && value <= maxValue;
    }

    @Override
    public String toString() {
        return "GradeRange{label='" + label + "', min=" + minValue + ", max=" + maxValue + "}";
    }
}
