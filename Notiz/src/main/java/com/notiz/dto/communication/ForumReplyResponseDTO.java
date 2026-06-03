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
public class ForumReplyResponseDTO {

    private String id;
    private String topicId;
    private String authorId;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

}
