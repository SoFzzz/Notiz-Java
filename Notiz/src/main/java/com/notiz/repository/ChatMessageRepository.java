package com.notiz.repository;

import com.notiz.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderIdAndReceiverIdAndTenantId(String senderId, String receiverId, String tenantId);

    long countByReceiverIdAndReadFalseAndTenantId(String receiverId, String tenantId);
}
