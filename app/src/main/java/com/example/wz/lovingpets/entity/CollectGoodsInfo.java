package com.example.wz.lovingpets.entity;


public class CollectGoodsInfo extends Collect {
	private Integer goodsId;
	private String goodsName;
	private Integer shopId;
	private Integer goodsdetailId;
	private float price;
	private String image;
	private String shopName;
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getGoodsdetailId() {
		return goodsdetailId;
	}
	public void setGoodsdetailId(Integer goodsdetailId) {
		this.goodsdetailId = goodsdetailId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
}
