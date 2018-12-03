package com.example.wz.lovingpets.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderInfo implements Serializable{
	private Integer id;//订单表ID
	private Integer userId;//用户表ID
	private String userName;//用户名
	private float amount;//总金额
	private Date createTime;//订单创建时间
	private Date tradingTime;//完成交易的时间
	private Integer orderState;//订单状态(交易成功、订单失效、待付款)
	private Integer addressId;//订收货地址id(交易成功、订单失效、待付款)
	private String receiver;
	private String contact;
	private String province;
	private String city;
	private String fullAddress;
	private List<OrderDetail> orderDetails;//该单订单下的所有商品
	
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTradingTime() {
		return tradingTime;
	}
	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

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
}
