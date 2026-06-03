package com.notiz.repository;

import com.notiz.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    List<Enrollment> findByStudentIdAndTenantId(String studentId, String tenantId);

    List<Enrollment> findByPeriodIdAndTenantId(String periodId, String tenantId);

    boolean existsByStudentIdAndPeriodIdAndTenantId(String studentId, String periodId, String tenantId);
}
