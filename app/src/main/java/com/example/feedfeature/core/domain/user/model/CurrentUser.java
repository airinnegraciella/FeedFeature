package com.example.feedfeature.core.domain.user.model;

public class CurrentUser {

    private String userId;
    private int employeeId;

    public CurrentUser(String userId, int employeeId) {
        this.userId = userId;
        this.employeeId = employeeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
