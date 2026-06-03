package com.notiz.service.impl;

import com.notiz.dto.auth.AuthResponseDTO;
import com.notiz.dto.auth.LoginDTO;
import com.notiz.dto.auth.ResetPasswordDTO;
import com.notiz.dto.auth.UserRegistrationDTO;
import com.notiz.dto.user.UserResponseDTO;
import com.notiz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponseDTO register(UserRegistrationDTO dto, String tenantId) {
        return null;
    }

    @Override
    public AuthResponseDTO login(LoginDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void logout(String userId, String token) {
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public void initiatePasswordReset(String email, String tenantId) {
    }

    @Override
    public void resetPassword(ResetPasswordDTO dto) {
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        return null;
    }
}
