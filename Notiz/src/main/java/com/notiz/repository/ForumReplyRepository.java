package com.notiz.repository;

import com.notiz.model.ForumReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumReplyRepository extends MongoRepository<ForumReply, String> {

    List<ForumReply> findByTopicIdAndTenantId(String topicId, String tenantId);

    long countByTopicId(String topicId);
}
