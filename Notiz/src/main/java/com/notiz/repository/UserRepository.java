package com.notiz.repository;

import com.notiz.model.User;
import com.notiz.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTenantId(String email, String tenantId);

    Page<User> findAllByTenantId(String tenantId, Pageable pageable);

    List<User> findByRoleAndTenantId(UserRole role, String tenantId);

    boolean existsByEmailAndTenantId(String email, String tenantId);
}
