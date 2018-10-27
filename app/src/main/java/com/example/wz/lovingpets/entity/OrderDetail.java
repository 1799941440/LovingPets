package com.example.wz.lovingpets.entity;

import java.io.Serializable;

/**
 * 订单详情表实体类
 * @author wz
 *
 */
public class OrderDetail implements Serializable{
	private Integer id;//订单详情表主键
	private Integer orderId;//订单表ID
	private Integer shopId;//商铺ID
	private String shopName;
	private Integer goodsDetailId;//商品详情表ID
	private String goodsName;
	private Integer count;//数量
	private String image;
	private float price;//商品成交单价
	private float amount;//单种商品总价
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getGoodsDetailId() {
		return goodsDetailId;
	}
	public void setGoodsDetailId(Integer goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}
