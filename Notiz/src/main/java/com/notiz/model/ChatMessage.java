package com.notiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessage extends BaseDocument {

    private String senderId;
    private String receiverId;
    private String content;
    private boolean read;

    public void markAsRead() {
        this.read = true;
    }
}
