package com.notiz.service.impl;

import com.notiz.dto.user.ProfileUpdateDTO;
import com.notiz.dto.user.UserCreateDTO;
import com.notiz.dto.user.UserResponseDTO;
import com.notiz.dto.user.UserUpdateDTO;
import com.notiz.model.enums.UserRole;
import com.notiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public UserResponseDTO updateUser(String id, UserUpdateDTO dto, String tenantId) {
        return null;
    }

    @Override
    public void deleteUser(String id, String tenantId) {
    }

    @Override
    public UserResponseDTO getUserById(String id, String tenantId) {
        return null;
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(String tenantId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public List<UserResponseDTO> getUsersByRole(UserRole role, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public UserResponseDTO updateProfile(String id, ProfileUpdateDTO dto, String tenantId) {
        return null;
    }
}
