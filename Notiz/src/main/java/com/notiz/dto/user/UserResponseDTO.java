package com.notiz.dto.user;

import com.notiz.model.enums.UserRole;
import com.notiz.model.enums.UserStatus;
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
public class UserResponseDTO {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String profileImageUrl;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;

}
