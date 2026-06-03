package com.notiz.dto.user;

import com.notiz.model.enums.UserStatus;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Email
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private UserStatus status;

}
