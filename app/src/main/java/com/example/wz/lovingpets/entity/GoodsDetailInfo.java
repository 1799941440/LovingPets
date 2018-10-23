package com.example.wz.lovingpets.entity;
/**
 * 商品详情表的所有信息，存放goods表和goodDetail表相关联的联合信息
 */
public class GoodsDetailInfo {
	private Integer id;//商品详情表的主键
	private String name;//商品名称
	private String petFamily;//该商品属于适合该宠物某大类（狗猫之别）
	private String petType;//商品适合宠物的状态（老幼情况）
	private String classify;//商品分类(主粮、医疗、服装、餐具、外出、玩具、其他)
	private Integer uploadId;//上传者id
	private String describe;//商品描述
	private Integer shopId;
	private String shopName;//店铺名称
	private Integer count;//商品数量
	private Float price;//商品定价
	private Integer sales;//商品销量
	private String image;//商品图片URL
	private String goodsState;//商品状态（下架、正常）
	private String unit;//商品单位
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUploadId() {
		return uploadId;
	}
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPetFamily() {
		return petFamily;
	}
	public void setPetFamily(String petFamily) {
		this.petFamily = petFamily;
	}
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
