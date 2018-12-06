package com.zhuangfei.expandedittext.listener;

import android.widget.EditText;
import android.widget.TextView;

/**
 * 当构造EditText、TextView时会调用该接口
 * 可以实现对其全局属性的设置
 * 比如可以设置字体大小、颜色等属性
 * Created by Liu ZhuangFei on 2018/2/28.
 */

public interface OnExpandBuildListener {

    public void onEditBuild(EditText editText);

    public void onTextBuild(TextView textView);

}
