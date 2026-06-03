package com.notiz.model;

public interface Auditable {
    String getAuditableId();
    String getAuditableType();
    String getAuditDescription();
}
