package com.notiz.model;

import com.notiz.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("Admin")
public class Admin extends User {

    private String managedInstitutionName;
    private boolean systemConfigAccess;

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    @Override
    public List<String> getPermissions() {
        return List.of("ALL_ACCESS");
    }

    @Override
    public String getDashboardType() {
        return "ADMIN_DASHBOARD";
    }
}
