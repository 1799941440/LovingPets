package com.example.wz.lovingpets.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    private int id;
    private String userId;
    private String userName;
    private String password;
    private int departmentId;
    private int roleType;
    private String pushId;
    private String head;
    private String photo;
    private int studentOrEmployee_id;
    private String beLongToGroup;
    private String departmentName;
    private String sectionName;
    private String collegeName;
    @Transient
    private List<Roles> roles;
    private String isDelete;
    private int collegeId;
    private boolean isWeb;
    private String token;
    private int authority;
    @Generated(hash = 1402632104)
    public User(int id, String userId, String userName, String password,
            int departmentId, int roleType, String pushId, String head,
            String photo, int studentOrEmployee_id, String beLongToGroup,
            String departmentName, String sectionName, String collegeName,
            String isDelete, int collegeId, boolean isWeb, String token,
            int authority) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.departmentId = departmentId;
        this.roleType = roleType;
        this.pushId = pushId;
        this.head = head;
        this.photo = photo;
        this.studentOrEmployee_id = studentOrEmployee_id;
        this.beLongToGroup = beLongToGroup;
        this.departmentName = departmentName;
        this.sectionName = sectionName;
        this.collegeName = collegeName;
        this.isDelete = isDelete;
        this.collegeId = collegeId;
        this.isWeb = isWeb;
        this.token = token;
        this.authority = authority;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    public int getRoleType() {
        return this.roleType;
    }
    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
    public String getPushId() {
        return this.pushId;
    }
    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getStudentOrEmployee_id() {
        return this.studentOrEmployee_id;
    }
    public void setStudentOrEmployee_id(int studentOrEmployee_id) {
        this.studentOrEmployee_id = studentOrEmployee_id;
    }
    public String getBeLongToGroup() {
        return this.beLongToGroup;
    }
    public void setBeLongToGroup(String beLongToGroup) {
        this.beLongToGroup = beLongToGroup;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getSectionName() {
        return this.sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    public String getCollegeName() {
        return this.collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    public String getIsDelete() {
        return this.isDelete;
    }
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
    public int getCollegeId() {
        return this.collegeId;
    }
    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }
    public boolean getIsWeb() {
        return this.isWeb;
    }
    public void setIsWeb(boolean isWeb) {
        this.isWeb = isWeb;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getAuthority() {
        return this.authority;
    }
    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public boolean isWeb() {
        return isWeb;
    }

    public void setWeb(boolean web) {
        isWeb = web;
    }
}