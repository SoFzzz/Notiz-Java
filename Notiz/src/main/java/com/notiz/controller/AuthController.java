package com.notiz.controller;

import com.notiz.dto.auth.AuthResponseDTO;
import com.notiz.dto.auth.LoginDTO;
import com.notiz.dto.auth.ResetPasswordDTO;
import com.notiz.dto.auth.UserRegistrationDTO;
import com.notiz.dto.user.UserResponseDTO;
import com.notiz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRegistrationDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(authService.register(dto, tenantId));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(authService.login(dto, tenantId));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(null, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @PostMapping("/password-reset/initiate")
    public ResponseEntity<Void> initiatePasswordReset(@RequestParam String email, @RequestHeader("X-Tenant-ID") String tenantId) {
        authService.initiatePasswordReset(email, tenantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/complete")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO dto) {
        authService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
