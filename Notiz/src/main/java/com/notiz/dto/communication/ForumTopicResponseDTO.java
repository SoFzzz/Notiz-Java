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
public class ForumTopicResponseDTO {

    private String id;
    private String title;
    private String content;
    private String authorId;
    private String authorName;
    private String category;
    private int replyCount;
    private LocalDateTime createdAt;

}
