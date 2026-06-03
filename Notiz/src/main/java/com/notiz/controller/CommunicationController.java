package com.notiz.controller;

import com.notiz.dto.communication.*;
import com.notiz.service.AnnouncementService;
import com.notiz.service.ChatMessageService;
import com.notiz.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final AnnouncementService announcementService;
    private final ChatMessageService chatMessageService;
    private final ForumService forumService;

    // --- Announcements ---
    @PostMapping("/announcements")
    public ResponseEntity<AnnouncementResponseDTO> createAnnouncement(@RequestBody AnnouncementCreateDTO dto, @RequestHeader("X-User-ID") String authorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(announcementService.createAnnouncement(dto, authorId, tenantId));
    }

    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        announcementService.deleteAnnouncement(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/announcements/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getAnnouncementById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id, tenantId));
    }

    @GetMapping("/announcements")
    public ResponseEntity<Page<AnnouncementResponseDTO>> getAllAnnouncements(@RequestHeader("X-Tenant-ID") String tenantId, Pageable pageable) {
        return ResponseEntity.ok(announcementService.getAllAnnouncements(tenantId, pageable));
    }

    @GetMapping("/announcements/audience/{audience}")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncementsForAudience(@PathVariable String audience, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(announcementService.getAnnouncementsForAudience(audience, tenantId));
    }

    // --- Chat Messages ---
    @PostMapping("/chat")
    public ResponseEntity<ChatMessageResponseDTO> sendMessage(@RequestBody ChatMessageCreateDTO dto, @RequestHeader("X-User-ID") String senderId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(chatMessageService.sendMessage(dto, senderId, tenantId));
    }

    @PostMapping("/chat/{messageId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String messageId, @RequestHeader("X-Tenant-ID") String tenantId) {
        chatMessageService.markAsRead(messageId, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<List<ChatMessageResponseDTO>> getConversation(@PathVariable String user1Id, @PathVariable String user2Id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(chatMessageService.getConversation(user1Id, user2Id, tenantId));
    }

    @GetMapping("/chat/unread/{userId}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable String userId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(chatMessageService.getUnreadCount(userId, tenantId));
    }

    // --- Forum ---
    @PostMapping("/forum/topics")
    public ResponseEntity<ForumTopicResponseDTO> createTopic(@RequestBody ForumTopicCreateDTO dto, @RequestHeader("X-User-ID") String authorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(forumService.createTopic(dto, authorId, tenantId));
    }

    @GetMapping("/forum/topics/{id}")
    public ResponseEntity<ForumTopicResponseDTO> getTopicById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(forumService.getTopicById(id, tenantId));
    }

    @GetMapping("/forum/topics")
    public ResponseEntity<Page<ForumTopicResponseDTO>> getAllTopics(@RequestHeader("X-Tenant-ID") String tenantId, Pageable pageable) {
        return ResponseEntity.ok(forumService.getAllTopics(tenantId, pageable));
    }

    @GetMapping("/forum/topics/category/{category}")
    public ResponseEntity<List<ForumTopicResponseDTO>> getTopicsByCategory(@PathVariable String category, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(forumService.getTopicsByCategory(category, tenantId));
    }

    @PostMapping("/forum/replies")
    public ResponseEntity<ForumReplyResponseDTO> addReply(@RequestBody ForumReplyCreateDTO dto, @RequestHeader("X-User-ID") String authorId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(forumService.addReply(dto, authorId, tenantId));
    }

    @GetMapping("/forum/topics/{topicId}/replies")
    public ResponseEntity<List<ForumReplyResponseDTO>> getTopicReplies(@PathVariable String topicId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(forumService.getTopicReplies(topicId, tenantId));
    }
}
