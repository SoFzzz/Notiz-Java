package com.notiz.service.impl;

import com.notiz.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyUser(String userId, String message, String preferredChannel, String tenantId) {
    }

    @Override
    public void notifyRole(String role, String message, String tenantId) {
    }

    @Override
    public void broadcast(String message, String tenantId) {
    }
}
