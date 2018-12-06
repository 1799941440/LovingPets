package com.zhuangfei.expandedittext.wrapper;

import android.util.Log;

import com.zhuangfei.expandedittext.entity.BaseEntity;
import com.zhuangfei.expandedittext.entity.EntityType;
import com.zhuangfei.expandedittext.ExpandEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liu ZhuangFei on 2018/2/27.
 */

public class DefaultImageWrapper extends ImageWrapper {

    @Override
    public String getPattern() {
        return "!\\[img\\]\\((\\S.*?)\\)";
    }

    @Override
    public String getImageWrapper(String str) {
        return "![img](" + str + ")";
    }

    @Override
    public void parse(ExpandEditText expandEditText, String text) {
        List<BaseEntity> entityList = new ArrayList<>();
        String nowStr = text;
        int preEnd = 0;
        String pattern=getPattern();
        Pattern r=Pattern.compile(pattern);
        Matcher matcher=r.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            String preString = nowStr.substring(preEnd, start);
            Log.i("prestring", "rr" + preString);
            preEnd = matcher.end();

            final String url = matcher.group(1);

            if (!preString.isEmpty()) {
                expandEditText.parseTextEntity(preString);
            }

            expandEditText.parseImageEntity(url);
        }
        if (preEnd < text.length()) {
            String string = nowStr.substring(preEnd);
            expandEditText.parseTextEntity(string);
        }
    }

}
