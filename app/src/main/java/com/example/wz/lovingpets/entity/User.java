package com.example.wz.lovingpets.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    private Integer id;//用户表主键
    private String userName;//用户名（不可重复）
    private String password;//账户密码
    private Integer identity;//用户身份（0：管理员 1：商家 2：普通用户）
    private String phone;//手机号码
    private String province;//地址的省名称或直辖市名
    private String city;//地址的省名称或直辖市名
    private String fullAddress;//市区以下的详细地址
    private float balance;//账户余额
    private String sex;//性别:男，女
    private Integer age;//年龄
    private Integer shopId;
    private String shopName;
    private Integer shoppingcartId;
    private String card;//身份证号
    private String name;//用户真实姓名
    private String icon;//用户头像
    @Generated(hash = 844711652)
    public User(Integer id, String userName, String password, Integer identity,
            String phone, String province, String city, String fullAddress,
            float balance, String sex, Integer age, Integer shopId, String shopName,
            Integer shoppingcartId, String card, String name, String icon) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.identity = identity;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.fullAddress = fullAddress;
        this.balance = balance;
        this.sex = sex;
        this.age = age;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shoppingcartId = shoppingcartId;
        this.card = card;
        this.name = name;
        this.icon = icon;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getIdentity() {
        return this.identity;
    }
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getFullAddress() {
        return this.fullAddress;
    }
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    public float getBalance() {
        return this.balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return this.age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getShopId() {
        return this.shopId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public Integer getShoppingcartId() {
        return this.shoppingcartId;
    }
    public void setShoppingcartId(Integer shoppingcartId) {
        this.shoppingcartId = shoppingcartId;
    }
    public String getCard() {
        return this.card;
    }
    public void setCard(String card) {
        this.card = card;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}