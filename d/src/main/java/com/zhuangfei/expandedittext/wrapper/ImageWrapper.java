package com.zhuangfei.expandedittext.wrapper;

/**
 * Created by Liu ZhuangFei on 2018/2/27.
 */

import com.zhuangfei.expandedittext.entity.BaseEntity;
import com.zhuangfei.expandedittext.ExpandEditText;

import java.util.List;
import java.util.regex.Matcher;

/**
 * 定义图片文字的包裹规则
 */
public abstract class ImageWrapper {

    public abstract String getPattern();

    public abstract String getImageWrapper(String str);

    public abstract void parse(ExpandEditText expandEditText,String text);
}
