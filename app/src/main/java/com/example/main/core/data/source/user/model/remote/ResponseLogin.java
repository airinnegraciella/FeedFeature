package com.example.main.core.data.source.user.model.remote;

import com.example.main.core.data.source.user.model.local.Employee;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    private String status;
    private String message;
    
    @SerializedName("result")
    private Employee employee;
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
