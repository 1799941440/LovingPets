package com.zhuangfei.expandedittext.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by Liu ZhuangFei on 2018/3/1.
 */

public class DimensonUtils {
    public static int getViewWidth(View view){
        view.measure(0,0);
        int width = view.getMeasuredWidth();
        return width;
    }

    public static int getViewHeight(View view){
        view.measure(0,0);
        int height = view.getMeasuredHeight();
        return height;
    }

    public static final int getWidthInPx(Context context) {
        final int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }
}
