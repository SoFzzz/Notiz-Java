package com.notiz.repository;

import com.notiz.model.GradeScale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeScaleRepository extends MongoRepository<GradeScale, String> {

    List<GradeScale> findAllByTenantId(String tenantId);

    Optional<GradeScale> findByNameAndTenantId(String name, String tenantId);
}
