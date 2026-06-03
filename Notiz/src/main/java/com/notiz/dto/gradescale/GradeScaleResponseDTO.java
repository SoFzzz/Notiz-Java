package com.notiz.dto.gradescale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeScaleResponseDTO {

    private String id;
    private String name;
    private List<GradeRangeDTO> ranges;

}
