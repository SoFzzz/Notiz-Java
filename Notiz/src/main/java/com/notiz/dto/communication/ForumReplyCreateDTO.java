package com.notiz.dto.communication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumReplyCreateDTO {

    @NotBlank
    private String topicId;

    @NotBlank
    private String content;

}
