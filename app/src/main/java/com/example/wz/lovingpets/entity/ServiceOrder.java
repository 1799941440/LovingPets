package com.example.wz.lovingpets.entity;

import java.util.Date;


public class ServiceOrder {
	private Integer id;//
	private Integer serviceId;//服务id
	private Integer userId;//预约的用户id
	private Date orderDate;//生成服务订单的时间
	private Date serverDate;//预约的时间
	private byte payWay;//支付方式 0-直接全部付清 1-待定的金额
	private float orderPrice;//支付的定金
	private float finalPrice;//最终花费
	private String orderMessage;//预约留言
	private String shopMessage;//店铺受理信息
	private byte state;//状态0-待店铺受理 1-等待服务/服务中 2-待支付 3-服务取消 4-服务完成待评价 5-服务完成已评价
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getServerDate() {
		return serverDate;
	}
	public void setServerDate(Date serverDate) {
		this.serverDate = serverDate;
	}
	public String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public String getShopMessage() {
		return shopMessage;
	}
	public void setShopMessage(String shopMessage) {
		this.shopMessage = shopMessage;
	}
	public byte getPayWay() {
		return payWay;
	}
	public void setPayWay(byte payWay) {
		this.payWay = payWay;
	}
	public float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public float getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	
}
