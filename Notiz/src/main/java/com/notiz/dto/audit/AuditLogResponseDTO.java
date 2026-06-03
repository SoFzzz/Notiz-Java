package com.notiz.dto.audit;

import com.notiz.model.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponseDTO {

    private String id;
    private String userId;
    private String userName;
    private String ipAddress;
    private AuditAction action;
    private String resource;
    private String resourceId;
    private LocalDateTime timestamp;

}
