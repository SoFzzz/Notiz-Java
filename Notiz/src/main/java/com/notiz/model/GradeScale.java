package com.notiz.model;

import com.notiz.model.embedded.GradeRange;
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
@Document(collection = "grade_scales")
public class GradeScale extends BaseDocument {

    private String name;
    private List<GradeRange> ranges = new ArrayList<>();

    public void addRange(GradeRange range) {
        ranges.add(range);
    }

    public String getQualitativeLabel(double value) {
        for (GradeRange range : ranges) {
            if (range.containsValue(value)) {
                return range.getLabel();
            }
        }
        return "N/A";
    }
}
