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
public class ChatMessageCreateDTO {

    @NotBlank
    private String receiverId;

    @NotBlank
    private String content;

}
