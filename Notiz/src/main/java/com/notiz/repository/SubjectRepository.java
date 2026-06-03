package com.notiz.repository;

import com.notiz.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {

    Optional<Subject> findByCodeAndTenantId(String code, String tenantId);

    List<Subject> findByProfessorIdAndTenantId(String professorId, String tenantId);

    Page<Subject> findAllByTenantId(String tenantId, Pageable pageable);

    boolean existsByCodeAndTenantId(String code, String tenantId);
}
