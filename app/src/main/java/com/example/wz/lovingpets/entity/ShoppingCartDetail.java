package com.example.wz.lovingpets.entity;

import java.io.Serializable;
/**
 * 购物车详情表的实体类
 * @author wz
 *
 */
public class ShoppingCartDetail implements Serializable{
	private Integer id;
	private Integer shoppingCartId;//购物车ID
	private Integer goodsDetailId;//商品详情表id
	private Integer count;//数量
	private float price;//价格
	private float total;//购物车单种商品的总价格
	private Integer shopId;//商品的店铺ID
	public ShoppingCartDetail() {}
	public ShoppingCartDetail(Integer id, Integer shoppingCartId, Integer goodsDetailId, Integer count, float price, float total, Integer shopId) {
		this.id = id;
		this.shoppingCartId = shoppingCartId;
		this.goodsDetailId = goodsDetailId;
		this.count = count;
		this.price = price;
		this.total = total;
		this.shopId = shopId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShoppingCartId() {
		return shoppingCartId;
	}
	public void setShoppingCartId(Integer shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
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
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
}
