package com.notiz.repository;

import com.notiz.model.ForumTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumTopicRepository extends MongoRepository<ForumTopic, String> {

    Page<ForumTopic> findAllByTenantId(String tenantId, Pageable pageable);

    List<ForumTopic> findByCategoryAndTenantId(String category, String tenantId);
}
