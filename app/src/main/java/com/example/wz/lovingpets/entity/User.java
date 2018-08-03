package com.example.wz.lovingpets.entity;

import java.util.List;

public class User {
    private int departmentId;
    private String userId;
    private String userName;
    private String password;
    private int roleType;
    private String pushId;
    private String head;
    private String photo;
    private int studentOrEmployee_id;
    private String beLongToGroup;
    private String departmentName;
    private String sectionName;
    private String collegeName;
    private List<Roles> roles;
    private String isDelete;
    private int collegeId;
    private boolean isWeb;
    private String token;
    private int id;
    private int authority;

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setStudentOrEmployee_id(int studentOrEmployee_id) {
        this.studentOrEmployee_id = studentOrEmployee_id;
    }

    public int getStudentOrEmployee_id() {
        return studentOrEmployee_id;
    }

    public void setBeLongToGroup(String beLongToGroup) {
        this.beLongToGroup = beLongToGroup;
    }

    public String getBeLongToGroup() {
        return beLongToGroup;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setIsWeb(boolean isWeb) {
        this.isWeb = isWeb;
    }

    public boolean getIsWeb() {
        return isWeb;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}