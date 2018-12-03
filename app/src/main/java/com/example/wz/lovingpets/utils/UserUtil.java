package com.example.wz.lovingpets.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.example.wz.lovingpets.entity.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class UserUtil {


    private SharedPreferences sp;
    private Editor editor;

    public UserUtil(Context context){
        sp = context.getSharedPreferences("user", MODE_PRIVATE);
    }

    public User getUser() {
        User user = new User();
        user.setId(sp.getInt("id",0));
        user.setUserName(sp.getString("userName",null));
        user.setPassword(sp.getString("password",""));
        user.setIdentity(sp.getInt("identify",2));
        user.setPhone(sp.getString("phone",""));
        user.setProvince(sp.getString("province",""));
        user.setCity(sp.getString("city",""));
        user.setFullAddress(sp.getString("fullAddress",""));
        user.setBalance(sp.getFloat("balance",0f));
        user.setSex(sp.getString("sex",""));
        user.setAge(sp.getInt("age",0));
        user.setShopId(sp.getInt("shopId",0));
        user.setShoppingcartId(sp.getInt("shoppingcartId",0));
        user.setShopName(sp.getString("shopName",""));
        user.setCard(sp.getString("card",""));
        user.setName(sp.getString("name",""));
        user.setIcon(sp.getString("icon",""));
        user.setCommomAddressId(sp.getInt("setCommomAddressId",0));
        return user;
    }

    public void saveUser(User user){
        editor = sp.edit();
        editor.putInt("id",user.getId());
        editor.putString("userName",user.getUserName());
        editor.putString("password", user.getPassword());
        editor.putInt("identity",user.getIdentity());
        editor.putString("phone", user.getPhone());
        editor.putString("province", user.getProvince());
        editor.putString("city", user.getCity());
        editor.putString("fullAddress", user.getFullAddress());
        editor.putFloat("balance",user.getBalance());
        editor.putString("sex", user.getSex());
        editor.putInt("age",user.getAge());
        editor.putInt("shopId",user.getShopId() == null ? 0:user.getShopId());
        editor.putInt("shoppingcartId",user.getShoppingcartId());
        editor.putString("shopName", user.getShopName());
        editor.putString("card", user.getCard());
        editor.putString("name", user.getName());
        editor.putString("icon", user.getIcon());
        editor.putInt("setCommomAddressId", user.getCommomAddressId());
        editor.commit();

    }

    public boolean cleanUser(){
        editor = sp.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
