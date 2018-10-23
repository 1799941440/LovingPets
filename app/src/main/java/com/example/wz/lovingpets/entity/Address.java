package com.example.wz.lovingpets.entity;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;//地址表主键
    private Integer userId;//用户表主键
    private String receiver;
    private String contact;
    private String province;//省或直辖市
    private String city;//市或区
    private String fullAddress;//具体地址
    private Integer isCommonAddress;//0-不是常用地址，1-常用地址
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getFullAddress() {
        return fullAddress;
    }
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    public Integer getIsCommonAddress() {
        return isCommonAddress;
    }
    public void setIsCommonAddress(Integer isCommonAddress) {
        this.isCommonAddress = isCommonAddress;
    }
}
