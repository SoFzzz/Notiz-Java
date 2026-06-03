package com.notiz.service.impl;

import com.notiz.dto.communication.ChatMessageCreateDTO;
import com.notiz.dto.communication.ChatMessageResponseDTO;
import com.notiz.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    @Override
    public ChatMessageResponseDTO sendMessage(ChatMessageCreateDTO dto, String senderId, String tenantId) {
        return null;
    }

    @Override
    public void markAsRead(String messageId, String tenantId) {
    }

    @Override
    public List<ChatMessageResponseDTO> getConversation(String user1Id, String user2Id, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public long getUnreadCount(String userId, String tenantId) {
        return 0;
    }
}
