package com.notiz.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
@RequiredArgsConstructor
public class WebSocketNotificationStrategy implements NotificationStrategy {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void send(String recipientId, String message) {
        messagingTemplate.convertAndSendToUser(
                recipientId,
                "/queue/notifications",
                message
        );
    }

    @Override
    public String getType() {
        return "WEBSOCKET";
    }

    @Override
    public boolean supports(String type) {
        return "WEBSOCKET".equalsIgnoreCase(type) || "ALL".equalsIgnoreCase(type);
    }
}
