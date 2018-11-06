package com.example.wz.lovingpets.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 宠物信息表的实体类
 * @author wz
 *
 */
public class PetInfo implements Serializable{
	private Integer id;//宠物表id
	private Integer userId;//主人id
	private String nickName;//宠物昵称
	private String state;//宠物状态(正常、征婚、需要被领养、已丢失、已去世)
	private String sex;//性别
	private Date birthday;//生日
	private String icon;//头像
	private String family;//大类名称
	private String className;//小类名称
	private Integer familyId;//宠物大类表ID
	private Integer classId;//宠物小类ID
	private String userName;//主任的用户名

	public PetInfo() {
	}

	public PetInfo(Integer id, Integer userId, String nickName, String state, String sex, Date birthday, String icon, Integer familyId, Integer classId) {
		this.id = id;
		this.userId = userId;
		this.nickName = nickName;
		this.state = state;
		this.sex = sex;
		this.birthday = birthday;
		this.icon = icon;
		this.familyId = familyId;
		this.classId = classId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	
}
