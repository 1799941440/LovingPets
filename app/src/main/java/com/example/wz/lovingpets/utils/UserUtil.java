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
        user.setUserId(sp.getString("userId",null));
        user.setAuthority(sp.getInt("authority",0));
        user.setPassword(sp.getString("password",null));
        user.setPushId(sp.getString("pushId",null));
        user.setRoleType(sp.getInt("role",0));
        user.setDepartmentId(sp.getInt("departmentId",0));
        user.setHead(sp.getString("head",null));
        user.setId(sp.getInt("id",0));
        user.setStudentOrEmployee_id(sp.getInt("studentOrEmployee_id",0));
        user.setPhoto(sp.getString("photo",null));
        user.setToken(sp.getString("token",null));
        user.setUserName(sp.getString("userName",null));
        return user;
    }

    public void saveUser(User user){
        editor = sp.edit();
        editor.putString("password", user.getPassword());
        int authority=0;
        if(user.getAuthority()!=0){
            authority= user.getAuthority();
        }
        editor.putInt("authority", authority);
        editor.putInt("id", user.getId());
        editor.putInt("departmentId", user.getDepartmentId());
        editor.putString("pushId", user.getPushId());
        editor.putString("userId", user.getUserId());
        editor.putString("token", user.getToken());
        editor.putString("userName", user.getUserName());
        editor.putString("head", user.getHead());
        editor.putString("photo", user.getPhoto());
        editor.putInt("role", user.getRoleType());
        editor.putInt("studentOrEmployee_id", user.getStudentOrEmployee_id());
        editor.commit();

    }

    public boolean cleanUser(){
        editor = sp.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
