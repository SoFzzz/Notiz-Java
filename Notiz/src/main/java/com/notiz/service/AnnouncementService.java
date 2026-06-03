package com.notiz.service;

import com.notiz.dto.communication.AnnouncementCreateDTO;
import com.notiz.dto.communication.AnnouncementResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementService {
    AnnouncementResponseDTO createAnnouncement(AnnouncementCreateDTO dto, String authorId, String tenantId);
    void deleteAnnouncement(String id, String tenantId);
    AnnouncementResponseDTO getAnnouncementById(String id, String tenantId);
    Page<AnnouncementResponseDTO> getAllAnnouncements(String tenantId, Pageable pageable);
    List<AnnouncementResponseDTO> getAnnouncementsForAudience(String audience, String tenantId);
}
