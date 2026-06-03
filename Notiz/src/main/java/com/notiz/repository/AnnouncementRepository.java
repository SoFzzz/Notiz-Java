package com.notiz.repository;

import com.notiz.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

    Page<Announcement> findAllByTenantId(String tenantId, Pageable pageable);

    List<Announcement> findByAuthorIdAndTenantId(String authorId, String tenantId);
}
