package com.notiz.model;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-address}")
    private String fromAddress;

    @Override
    public void send(String recipientId, String message) {
        // Implementation with SendGrid API would go here
        System.out.println("Sending email to " + recipientId + ": " + message);
    }

    @Override
    public String getType() {
        return "EMAIL";
    }

    @Override
    public boolean supports(String type) {
        return "EMAIL".equalsIgnoreCase(type) || "ALL".equalsIgnoreCase(type);
    }
}
