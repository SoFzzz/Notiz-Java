package com.notiz.model;

import com.notiz.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("Professor")
public class Professor extends User {

    private String department;
    private String specialization;
    private List<String> assignedSubjectIds = new ArrayList<>();

    @Override
    public UserRole getRole() {
        return UserRole.PROFESSOR;
    }

    @Override
    public List<String> getPermissions() {
        return List.of("MANAGE_GRADES", "VIEW_STUDENTS", "MANAGE_SUBJECTS");
    }

    @Override
    public String getDashboardType() {
        return "PROFESSOR_DASHBOARD";
    }

    public void assignSubject(String subjectId) {
        if (!assignedSubjectIds.contains(subjectId)) {
            assignedSubjectIds.add(subjectId);
        }
    }

    public void removeSubject(String subjectId) {
        assignedSubjectIds.remove(subjectId);
    }

    public boolean isAssignedTo(String subjectId) {
        return assignedSubjectIds.contains(subjectId);
    }
}
