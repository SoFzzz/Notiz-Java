package com.notiz.controller;

import com.notiz.dto.user.ProfileUpdateDTO;
import com.notiz.dto.user.UserCreateDTO;
import com.notiz.dto.user.UserResponseDTO;
import com.notiz.dto.user.UserUpdateDTO;
import com.notiz.model.enums.UserRole;
import com.notiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.createUser(dto, tenantId));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestHeader("X-Tenant-ID") String tenantId, Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(tenantId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.getUserById(id, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.updateUser(id, dto, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id, @RequestHeader("X-Tenant-ID") String tenantId) {
        userService.deleteUser(id, tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable UserRole role, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.getUsersByRole(role, tenantId));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable String id, @RequestBody ProfileUpdateDTO dto, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.updateProfile(id, dto, tenantId));
    }
}
