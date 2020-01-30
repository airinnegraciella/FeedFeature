package com.example.main.core.data.source.user.model.local;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmployeeId")
    private int employeeId;
    @SerializedName("EmployeeNumber")
    private String employeeNumber;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("InitialName")
    private String initialName;
    @SerializedName("IdentityNumber")
    private String identityNumber;
    @SerializedName("BirthPlace")
    private String birthPlace;
    @SerializedName("BirthDate")
    private String birthDate;
    @SerializedName("GenderName")
    private String genderName;
    @SerializedName("ReligionName")
    private String religionName;
    @SerializedName("MaritalName")
    private String maritalName;
    @SerializedName("NPWP")
    private String npwp;
    @SerializedName("BPJSKesehatan")
    private String bpjsKesehatan;
    @SerializedName("BPJSKetenagakerjaan")
    private String bpjsKetenagakerjaan;
    @SerializedName("InsuranceNumber")
    private String insuranceNumber;
    @SerializedName("MobilePhone")
    private String mobilePhone;
    @SerializedName("HomePhone")
    private String homePhone;
    @SerializedName("Extention")
    private String extention;
    @SerializedName("WorkEmail")
    private String workEmail;
    @SerializedName("PersonalEmail")
    private String personalEmail;
    @SerializedName("IdentityAddress")
    private String identityAddress;
    @SerializedName("IdentityCity")
    private String identityCity;
    @SerializedName("IdentityPostalCode")
    private String identityPostalCode;
    @SerializedName("CurrentAddress")
    private String currentAddress;
    @SerializedName("CurrentCity")
    private String currentCity;
    @SerializedName("CurrentPostalCode")
    private String currentPostalCode;
    @SerializedName("CV")
    private String cv;
    @SerializedName("ImageProfile")
    private String imageProfile;
    @SerializedName("LeaveQuota")
    private String leaveQuota;
    @SerializedName("Active")
    private int active;
    @SerializedName("CreatedBy")
    private String createdBy;
    @SerializedName("CreatedDate")
    private String createdDate;
    @SerializedName("UpdatedBy")
    private String updatedBy;
    @SerializedName("UpdatedDate")
    private String updatedDate;
    
    public Employee(int employeeId, String employeeNumber, String fullName, String initialName, String identityNumber, String birthPlace, String birthDate, String genderName, String religionName, String maritalName, String npwp, String bpjsKesehatan, String bpjsKetenagakerjaan, String insuranceNumber, String mobilePhone, String homePhone, String extention, String workEmail, String personalEmail, String identityAddress, String identityCity, String identityPostalCode, String currentAddress, String currentCity, String currentPostalCode, String cv, String imageProfile, String leaveQuota, int active, String createdBy, String createdDate, String updatedBy, String updatedDate) {
        this.employeeId = employeeId;
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.initialName = initialName;
        this.identityNumber = identityNumber;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.genderName = genderName;
        this.religionName = religionName;
        this.maritalName = maritalName;
        this.npwp = npwp;
        this.bpjsKesehatan = bpjsKesehatan;
        this.bpjsKetenagakerjaan = bpjsKetenagakerjaan;
        this.insuranceNumber = insuranceNumber;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.extention = extention;
        this.workEmail = workEmail;
        this.personalEmail = personalEmail;
        this.identityAddress = identityAddress;
        this.identityCity = identityCity;
        this.identityPostalCode = identityPostalCode;
        this.currentAddress = currentAddress;
        this.currentCity = currentCity;
        this.currentPostalCode = currentPostalCode;
        this.cv = cv;
        this.imageProfile = imageProfile;
        this.leaveQuota = leaveQuota;
        this.active = active;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getInitialName() {
        return initialName;
    }
    
    public void setInitialName(String initialName) {
        this.initialName = initialName;
    }
    
    public String getIdentityNumber() {
        return identityNumber;
    }
    
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
    
    public String getBirthPlace() {
        return birthPlace;
    }
    
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    public String getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getGenderName() {
        return genderName;
    }
    
    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
    
    public String getReligionName() {
        return religionName;
    }
    
    public void setReligionName(String religionName) {
        this.religionName = religionName;
    }
    
    public String getMaritalName() {
        return maritalName;
    }
    
    public void setMaritalName(String maritalName) {
        this.maritalName = maritalName;
    }
    
    public String getNpwp() {
        return npwp;
    }
    
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }
    
    public String getBpjsKesehatan() {
        return bpjsKesehatan;
    }
    
    public void setBpjsKesehatan(String bpjsKesehatan) {
        this.bpjsKesehatan = bpjsKesehatan;
    }
    
    public String getBpjsKetenagakerjaan() {
        return bpjsKetenagakerjaan;
    }
    
    public void setBpjsKetenagakerjaan(String bpjsKetenagakerjaan) {
        this.bpjsKetenagakerjaan = bpjsKetenagakerjaan;
    }
    
    public String getInsuranceNumber() {
        return insuranceNumber;
    }
    
    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }
    
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public String getHomePhone() {
        return homePhone;
    }
    
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    
    public String getExtention() {
        return extention;
    }
    
    public void setExtention(String extention) {
        this.extention = extention;
    }
    
    public String getWorkEmail() {
        return workEmail;
    }
    
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
    
    public String getPersonalEmail() {
        return personalEmail;
    }
    
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }
    
    public String getIdentityAddress() {
        return identityAddress;
    }
    
    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }
    
    public String getIdentityCity() {
        return identityCity;
    }
    
    public void setIdentityCity(String identityCity) {
        this.identityCity = identityCity;
    }
    
    public String getIdentityPostalCode() {
        return identityPostalCode;
    }
    
    public void setIdentityPostalCode(String identityPostalCode) {
        this.identityPostalCode = identityPostalCode;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
    
    public String getCurrentCity() {
        return currentCity;
    }
    
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
    
    public String getCurrentPostalCode() {
        return currentPostalCode;
    }
    
    public void setCurrentPostalCode(String currentPostalCode) {
        this.currentPostalCode = currentPostalCode;
    }
    
    public String getCv() {
        return cv;
    }
    
    public void setCv(String cv) {
        this.cv = cv;
    }
    
    public String getImageProfile() {
        return imageProfile;
    }
    
    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
    
    public String getLeaveQuota() {
        return leaveQuota;
    }
    
    public void setLeaveQuota(String leaveQuota) {
        this.leaveQuota = leaveQuota;
    }
    
    public int getActive() {
        return active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
