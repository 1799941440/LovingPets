package com.example.wz.lovingpets.entity;

import java.util.Date;
import java.util.List;


public class ThemeInfo{
	private Integer id;//id
	private String title;//主题标题
	private String content;//主题内容
	private Date pushTime;//发布时间
	private Integer ownerId;//发布者id
	private Integer petId;//发布者id
	private Integer zan; //点赞数
	private Integer collection; //收藏数
	private Integer comment; //评论数
	private Integer commentable;//是否可评论（0-可以，1-不可评论）
	private Integer isDelete;//逻辑删除（0-正常1-用户自己逻辑删除）
	private String deleteReason;
	
//	用户信息
	private Integer age;
	private Integer isCollect;
	private String sex;
	private String icon;
	private String userName;
	
//	宠物信息
	private String nickName;
	private String petSex;
	private Date birthday;//宠物生日
	private String className;
	private String family;
//	评论信息
	private List<ThemeCommentInfo> comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getZan() {
		return zan;
	}

	public void setZan(Integer zan) {
		this.zan = zan;
	}

	public Integer getCollection() {
		return collection;
	}

	public void setCollection(Integer collection) {
		this.collection = collection;
	}

	public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public Integer getCommentable() {
		return commentable;
	}

	public void setCommentable(Integer commentable) {
		this.commentable = commentable;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public List<ThemeCommentInfo> getComments() {
		return comments;
	}

	public void setComments(List<ThemeCommentInfo> comments) {
		this.comments = comments;
	}

	public Integer getAge() {
		return age;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPetSex() {
		return petSex;
	}

	public void setPetSex(String petSex) {
		this.petSex = petSex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}
}
