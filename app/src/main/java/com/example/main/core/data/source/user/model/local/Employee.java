package com.example.main.core.data.source.user.model.local;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmployeeId")
    public int employeeId;
    @SerializedName("EmployeeNumber")
    public String employeeNumber;
    @SerializedName("FullName")
    public String fullName;
    @SerializedName("InitialName")
    public String initialName;
    @SerializedName("IdentityNumber")
    public String identityNumber;
    @SerializedName("BirthPlace")
    public String birthPlace;
    @SerializedName("BirthDate")
    public String birthDate;
    @SerializedName("GenderName")
    public String genderName;
    @SerializedName("ReligionName")
    public String religionName;
    @SerializedName("MaritalName")
    public String maritalName;
    @SerializedName("NPWP")
    public String npwp;
    @SerializedName("BPJSKesehatan")
    public String bpjsKesehatan;
    @SerializedName("BPJSKetenagakerjaan")
    public String bpjsKetenagakerjaan;
    @SerializedName("InsuranceNumber")
    public String insuranceNumber;
    @SerializedName("MobilePhone")
    public String mobilePhone;
    @SerializedName("HomePhone")
    public String homePhone;
    @SerializedName("Extention")
    public String extention;
    @SerializedName("WorkEmail")
    public String workEmail;
    @SerializedName("PersonalEmail")
    public String personalEmail;
    @SerializedName("IdentityAddress")
    public String identityAddress;
    @SerializedName("IdentityCity")
    public String identityCity;
    @SerializedName("IdentityPostalCode")
    public String identityPostalCode;
    @SerializedName("CurrentAddress")
    public String currentAddress;
    @SerializedName("CurrentCity")
    public String currentCity;
    @SerializedName("CurrentPostalCode")
    public String currentPostalCode;
    @SerializedName("CV")
    public String cv;
    @SerializedName("ImageProfile")
    public String imageProfile;
    @SerializedName("LeaveQuota")
    public String leaveQuota;
    @SerializedName("Active")
    public int active;
    @SerializedName("CreatedBy")
    public String createdBy;
    @SerializedName("CreatedDate")
    public String createdDate;
    @SerializedName("UpdatedBy")
    public String updatedBy;
    @SerializedName("UpdatedDate")
    public String updatedDate;

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
