package com.notiz.repository;

import com.notiz.model.AcademicPeriod;
import com.notiz.model.enums.PeriodStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicPeriodRepository extends MongoRepository<AcademicPeriod, String> {

    List<AcademicPeriod> findByStatusAndTenantId(PeriodStatus status, String tenantId);

    List<AcademicPeriod> findAllByTenantId(String tenantId);

    Optional<AcademicPeriod> findByNameAndTenantId(String name, String tenantId);
}
