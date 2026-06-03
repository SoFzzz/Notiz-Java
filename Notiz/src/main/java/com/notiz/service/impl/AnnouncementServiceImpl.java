package com.notiz.service.impl;

import com.notiz.dto.communication.AnnouncementCreateDTO;
import com.notiz.dto.communication.AnnouncementResponseDTO;
import com.notiz.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    @Override
    public AnnouncementResponseDTO createAnnouncement(AnnouncementCreateDTO dto, String authorId, String tenantId) {
        return null;
    }

    @Override
    public void deleteAnnouncement(String id, String tenantId) {
    }

    @Override
    public AnnouncementResponseDTO getAnnouncementById(String id, String tenantId) {
        return null;
    }

    @Override
    public Page<AnnouncementResponseDTO> getAllAnnouncements(String tenantId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public List<AnnouncementResponseDTO> getAnnouncementsForAudience(String audience, String tenantId) {
        return Collections.emptyList();
    }
}
