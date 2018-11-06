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
    public static final int ADD_TO_CART = 0x123;//加入购物车
}
