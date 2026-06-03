package com.notiz.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponseDTO {

    private String id;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String content;
    private boolean read;
    private LocalDateTime timestamp;

}
