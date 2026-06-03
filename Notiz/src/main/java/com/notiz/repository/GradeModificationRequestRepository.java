package com.notiz.repository;

import com.notiz.model.GradeModificationRequest;
import com.notiz.model.enums.ModificationRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeModificationRequestRepository extends MongoRepository<GradeModificationRequest, String> {

    List<GradeModificationRequest> findByProfessorIdAndTenantId(String professorId, String tenantId);

    List<GradeModificationRequest> findByStatusAndTenantId(ModificationRequestStatus status, String tenantId);
}
