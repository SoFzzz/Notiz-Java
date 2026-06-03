package com.notiz.repository;

import com.notiz.model.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends MongoRepository<Grade, String> {

    List<Grade> findByStudentIdAndTenantId(String studentId, String tenantId);

    List<Grade> findBySubjectIdAndPeriodIdAndTenantId(String subjectId, String periodId, String tenantId);

    Page<Grade> findByCreatedByAndTenantId(String createdBy, String tenantId, Pageable pageable);

    Page<Grade> findAllByTenantId(String tenantId, Pageable pageable);

    void deleteAllBySubjectIdAndTenantId(String subjectId, String tenantId);
}
