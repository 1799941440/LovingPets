package com.example.wz.lovingpets.entity;

import java.util.Date;

/**
 * 主题表实体类
 * @author wz
 *
 */
public class Theme {
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
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
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public String getDeleteReason() {
		return deleteReason;
	}
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
}
