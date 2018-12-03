package com.example.wz.lovingpets.entity;

public class Server {
	private Integer id;//
	private String serviceName;//服务名称
	private float price;//价格
	private float orderPrice;//预约金额
	private byte payWay;//支付方式 0-直接全部付清 1-待定的金额
	private Integer shopId;//店铺id
	private Integer commentTimes;//评价次数
	private Integer commentStars;//评价总星数
	private Integer sales;//销量
	private String images;
	private String describe;//服务描述
	private byte state;//状态0-正常 1-暂停服务
	private Integer type;//服务分类
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getCommentTimes() {
		return commentTimes;
	}
	public byte getPayWay() {
		return payWay;
	}
	public void setPayWay(byte payWay) {
		this.payWay = payWay;
	}
	public void setCommentTimes(Integer commentTimes) {
		this.commentTimes = commentTimes;
	}
	public Integer getCommentStars() {
		return commentStars;
	}
	public void setCommentStars(Integer commentStars) {
		this.commentStars = commentStars;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
