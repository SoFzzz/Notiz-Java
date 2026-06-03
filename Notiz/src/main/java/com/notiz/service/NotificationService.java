package com.notiz.service;

public interface NotificationService {
    void notifyUser(String userId, String message, String preferredChannel, String tenantId);
    void notifyRole(String role, String message, String tenantId);
    void broadcast(String message, String tenantId);
}
