package com.example.feedfeature.core.domain.user.repository;

import com.example.feedfeature.core.base.ICallback;
import com.example.feedfeature.core.domain.user.model.CurrentUser;

import java.util.List;

public interface UserRepository {
//    void getCurrentServerDateTime(ICallback<ResponseServerDateTime> callback);

    void getCurrentUserLogin(ICallback<CurrentUser> callback);

//    void getEmployeeById(int employeeId , ICallback<ResponseEmployeeById> callback);

//    void getLastWorkHistoryByEmployeeId(int employeeId , ICallback<ResponseLastWorkHistoryByEmployeeId> callback);

//    void getAllEmployeeByArrayId(List<Integer> employeeIdArray , ICallback<ResponseAllEmployeeByArrayId> callback);

//    void getAllEmployee(String keyword , boolean isAllEmployeeLevel , List<Integer> employeeLevelIdArray , boolean isAllDepartment , List<Integer> departmentIdArray , List<Integer> employeeIdExceptArray , boolean isForApprover , ICallback<ResponseAllEmployee> callback);

//    void getApprovalRule(int employeeId , int moduleApprovalId , ICallback<ResponseApprovalRule> callback);

//    void getAllAdminId(ICallback<ResponseAllAdminId> callback);

//    void getEndorserByEmployeeId(int employeeId , ICallback<ResponseEndorserByEmployeeId> callback);

//    void getAllMentorByEmployeeId(int employeeId , ICallback<ResponseAllMentorByEmployeeId> callback);

//    void getAllMenteeByEmployeeId(int employeeId , ICallback<ResponseAllMenteeByEmployeeId> callback);

//    void readNotification(int notificationId , ICallback<ResponseReadNotification> callback);
}
