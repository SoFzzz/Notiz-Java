package com.notiz.model;

import com.notiz.model.enums.UserRole;
import com.notiz.model.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("User")
public abstract class User extends BaseDocument implements Auditable, Exportable {

    @Indexed
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String address;
    protected String profileImageUrl;
    protected UserStatus status;
    protected int failedLoginAttempts;
    protected LocalDateTime lockedUntil;
    protected String googleOAuthId;

    public abstract UserRole getRole();
    public abstract List<String> getPermissions();
    public abstract String getDashboardType();

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAccountLocked() {
        return lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now());
    }

    public void incrementFailedAttempts() {
        this.failedLoginAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedLoginAttempts = 0;
        this.lockedUntil = null;
    }

    public void deactivate() {
        this.status = UserStatus.DISABLED;
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    // --- Auditable Implementation ---
    @Override
    public String getAuditableId() {
        return getId();
    }

    @Override
    public String getAuditableType() {
        return "User";
    }

    @Override
    public String getAuditDescription() {
        return "User: " + getFullName() + " (" + email + ")";
    }

    // --- Exportable Implementation ---
    @Override
    public List<Object> toExportRow() {
        List<Object> row = new ArrayList<>();
        row.add(id);
        row.add(firstName);
        row.add(lastName);
        row.add(email);
        row.add(getRole().name());
        row.add(status.name());
        return row;
    }

    @Override
    public List<String> getExportHeaders() {
        return List.of("ID", "First Name", "Last Name", "Email", "Role", "Status");
    }

    @Override
    public String getExportFileName() {
        return "users_export_" + LocalDateTime.now() + ".csv";
    }
}
