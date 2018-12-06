package com.zhuangfei.expandedittext.utils;

import android.content.Context;

/**
 * Created by Liu ZhuangFei on 2018/2/27.
 */

public class DipUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
