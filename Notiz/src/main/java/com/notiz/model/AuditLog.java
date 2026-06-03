package com.notiz.model;

import com.notiz.model.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "audit_logs")
public class AuditLog extends BaseDocument {

    private String userId;
    private String ipAddress;
    private AuditAction action;
    private String resource;
    private String resourceId;

}
