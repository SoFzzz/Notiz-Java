package com.notiz.dto.gradescale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeScaleUpdateDTO {

    private String name;
    private List<GradeRangeDTO> ranges;

}
