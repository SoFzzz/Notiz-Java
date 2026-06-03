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
@TypeAlias("Parent")
public class Parent extends User {

    private List<String> studentIds = new ArrayList<>();
    private String relationship;

    @Override
    public UserRole getRole() {
        return UserRole.PARENT;
    }

    @Override
    public List<String> getPermissions() {
        return List.of("VIEW_CHILD_GRADES", "VIEW_CHILD_SCHEDULE");
    }

    @Override
    public String getDashboardType() {
        return "PARENT_DASHBOARD";
    }

    public void addStudent(String studentId) {
        if (!studentIds.contains(studentId)) {
            studentIds.add(studentId);
        }
    }

    public void removeStudent(String studentId) {
        studentIds.remove(studentId);
    }

    public boolean isTutorOf(String studentId) {
        return studentIds.contains(studentId);
    }
}
