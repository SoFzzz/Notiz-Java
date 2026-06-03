package com.notiz.service;

import com.notiz.dto.user.ProfileUpdateDTO;
import com.notiz.dto.user.UserCreateDTO;
import com.notiz.dto.user.UserResponseDTO;
import com.notiz.dto.user.UserUpdateDTO;
import com.notiz.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateDTO dto, String tenantId);
    UserResponseDTO updateUser(String id, UserUpdateDTO dto, String tenantId);
    void deleteUser(String id, String tenantId);
    UserResponseDTO getUserById(String id, String tenantId);
    Page<UserResponseDTO> getAllUsers(String tenantId, Pageable pageable);
    List<UserResponseDTO> getUsersByRole(UserRole role, String tenantId);
    UserResponseDTO updateProfile(String id, ProfileUpdateDTO dto, String tenantId);
}
