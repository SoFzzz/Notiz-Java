package com.notiz.service;

import com.notiz.dto.auth.AuthResponseDTO;
import com.notiz.dto.auth.LoginDTO;
import com.notiz.dto.auth.ResetPasswordDTO;
import com.notiz.dto.auth.UserRegistrationDTO;
import com.notiz.dto.user.UserResponseDTO;

public interface AuthService {
    AuthResponseDTO register(UserRegistrationDTO dto, String tenantId);
    AuthResponseDTO login(LoginDTO dto, String tenantId);
    void logout(String userId, String token);
    AuthResponseDTO refreshToken(String refreshToken);
    void initiatePasswordReset(String email, String tenantId);
    void resetPassword(ResetPasswordDTO dto);
    UserResponseDTO getCurrentUser();
}
