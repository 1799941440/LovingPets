package com.example.wz.lovingpets.common;

public class EventCodes {
    public static final int LOGIN_SUCCESS = 0x1;//登录成功
    public static final int SAVE_ADDRESS = 0x3;//保存收货地址
    public static final int LOADED_ORDER = 0x4;//获取订单成功
    public static final int DEL_ORDER = 0x5;//删除订单
    public static final int PAY_ORDER = 0x6;//支付订单
    public static final int RECEIVE_ORDER = 0x7;//确认收货
    public static final int DEL_PET = 0x8;//删除宠物
    public static final int MANAGE_PET = 0x9;//修改或删除宠物通知前一个更新数据
    public static final int OPEN_GOODSDETAIL_DIALOG = 0x10;//商品列表打开详情
    public static final int SWITCH_COLLECT = 0x11;//点击收藏或者取消收藏
    public static final int BUY_GOODS = 0x12;//点击直接购买按钮
    public static final int LOADED_SERVER_ORDER = 0x13;//获取服务订单成功
    public static final int CANCEL_SERVER_ORDER = 0x14;//取消服务订单
    public static final int DELAY_SERVER_ORDER = 0x15;//延后服务订单
    public static final int PAY_SERVER_ORDER = 0x16;//支付待定金额服务订单
    public static final int DEL_SERVER_ORDER = 0x17;//删除服务订单
    public static final int COMMENT_SERVER_ORDER = 0x18;//评论服务订单
    public static final int ADD_TO_CART = 0x123;//加入购物车
}
