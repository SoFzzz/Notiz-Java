package com.notiz.repository;

import com.notiz.model.AuditLog;
import com.notiz.model.enums.AuditAction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    List<AuditLog> findByUserIdAndTenantId(String userId, String tenantId);

    List<AuditLog> findByCreatedAtBetweenAndTenantId(LocalDateTime start, LocalDateTime end, String tenantId);

    List<AuditLog> findByActionAndTenantId(AuditAction action, String tenantId);

    long countByActionAndTenantId(AuditAction action, String tenantId);
}
