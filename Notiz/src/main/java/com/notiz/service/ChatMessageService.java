package com.notiz.service;

import com.notiz.dto.communication.ChatMessageCreateDTO;
import com.notiz.dto.communication.ChatMessageResponseDTO;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponseDTO sendMessage(ChatMessageCreateDTO dto, String senderId, String tenantId);
    void markAsRead(String messageId, String tenantId);
    List<ChatMessageResponseDTO> getConversation(String user1Id, String user2Id, String tenantId);
    long getUnreadCount(String userId, String tenantId);
}
