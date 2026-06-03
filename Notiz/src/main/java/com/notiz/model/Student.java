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
@TypeAlias("Student")
public class Student extends User {

    private String studentCode;
    private int enrollmentYear;
    private String guardianId;

    @Override
    public UserRole getRole() {
        return UserRole.STUDENT;
    }

    @Override
    public List<String> getPermissions() {
        return List.of("VIEW_GRADES", "PARTICIPATE_FORUM");
    }

    @Override
    public String getDashboardType() {
        return "STUDENT_DASHBOARD";
    }

    public boolean hasGuardian() {
        return guardianId != null && !guardianId.isEmpty();
    }
}
