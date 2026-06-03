package com.notiz.model;

public interface NotificationStrategy {
    void send(String recipientId, String message);
    String getType();
    boolean supports(String type);
}
