package com.example.wz.lovingpets.entity;

import java.text.DecimalFormat;
import java.util.List;


public class ServiceInfo extends Server {
	private String shopName;
	private String address;
	private String serviceStar;
	private List<ServiceComment> commentList; 
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getServiceStar() {
		if(this.serviceStar == null){
			DecimalFormat df = new DecimalFormat("#0.00");
			if(getCommentTimes() == 0){
				return "0";
			}
			serviceStar = df.format(getCommentStars()/(float)getCommentTimes());
		}
		return serviceStar;
	}
	public void setServiceStar(String serviceStar) {
		DecimalFormat df = new DecimalFormat("#0.00");
		this.serviceStar = df.format(getCommentStars()/(float)getCommentTimes());
	}
	public List<ServiceComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<ServiceComment> commentList) {
		this.commentList = commentList;
	}
}
