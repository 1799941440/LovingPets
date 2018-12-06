package com.zhuangfei.expandedittext.entity;

/**
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class BaseEntity {

    int type=-1;

    public void setType(int type){this.type=type;}

    public void setText(String text){}

    public String getText(){
        return "";
    }

    public int getType(){
        return type;
    }
}
