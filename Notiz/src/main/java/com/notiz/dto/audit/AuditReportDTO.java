package com.notiz.dto.audit;

import com.notiz.model.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditReportDTO {

    private long totalActions;
    private Map<AuditAction, Long> actionsByType;
    private String dateRange;
    private List<AuditLogResponseDTO> entries;

}
