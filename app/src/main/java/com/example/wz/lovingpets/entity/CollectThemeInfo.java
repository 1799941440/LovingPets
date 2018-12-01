package com.example.wz.lovingpets.entity;

import java.util.Date;


public class CollectThemeInfo extends Collect {
	private Integer themeId;
	private Integer ownerId;
	private Integer commentTimes;
	private Integer collectTimes;
	private String title;
	private Date pushTime;
	private String content;
	private String userName;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getThemeId() {
		return themeId;
	}
	public Integer getCommentTimes() {
		return commentTimes;
	}
	public void setCommentTimes(Integer commentTimes) {
		this.commentTimes = commentTimes;
	}
	public Integer getCollectTimes() {
		return collectTimes;
	}
	public void setCollectTimes(Integer collectTimes) {
		this.collectTimes = collectTimes;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
