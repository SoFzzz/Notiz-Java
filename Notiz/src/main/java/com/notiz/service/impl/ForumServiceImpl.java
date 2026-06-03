package com.notiz.service.impl;

import com.notiz.dto.communication.ForumReplyCreateDTO;
import com.notiz.dto.communication.ForumReplyResponseDTO;
import com.notiz.dto.communication.ForumTopicCreateDTO;
import com.notiz.dto.communication.ForumTopicResponseDTO;
import com.notiz.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    @Override
    public ForumTopicResponseDTO createTopic(ForumTopicCreateDTO dto, String authorId, String tenantId) {
        return null;
    }

    @Override
    public ForumTopicResponseDTO getTopicById(String id, String tenantId) {
        return null;
    }

    @Override
    public Page<ForumTopicResponseDTO> getAllTopics(String tenantId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public List<ForumTopicResponseDTO> getTopicsByCategory(String category, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public ForumReplyResponseDTO addReply(ForumReplyCreateDTO dto, String authorId, String tenantId) {
        return null;
    }

    @Override
    public List<ForumReplyResponseDTO> getTopicReplies(String topicId, String tenantId) {
        return Collections.emptyList();
    }
}
