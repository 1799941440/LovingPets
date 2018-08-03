package com.example.wz.lovingpets.entity;

import java.util.List;


public class Roles {

    private String departmentId;
    private String role;
    private String type;
    private String addRoleId;
    private String addUserId;
    private List<String> menus;
    private String departmentName;
    private int id;

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAddRoleId(String addRoleId) {
        this.addRoleId = addRoleId;
    }

    public String getAddRoleId() {
        return addRoleId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

