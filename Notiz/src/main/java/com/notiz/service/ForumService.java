package com.notiz.service;

import com.notiz.dto.communication.ForumReplyCreateDTO;
import com.notiz.dto.communication.ForumReplyResponseDTO;
import com.notiz.dto.communication.ForumTopicCreateDTO;
import com.notiz.dto.communication.ForumTopicResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumService {
    ForumTopicResponseDTO createTopic(ForumTopicCreateDTO dto, String authorId, String tenantId);
    ForumTopicResponseDTO getTopicById(String id, String tenantId);
    Page<ForumTopicResponseDTO> getAllTopics(String tenantId, Pageable pageable);
    List<ForumTopicResponseDTO> getTopicsByCategory(String category, String tenantId);
    
    ForumReplyResponseDTO addReply(ForumReplyCreateDTO dto, String authorId, String tenantId);
    List<ForumReplyResponseDTO> getTopicReplies(String topicId, String tenantId);
}
