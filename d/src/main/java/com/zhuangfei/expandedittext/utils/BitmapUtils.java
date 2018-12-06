package com.zhuangfei.expandedittext.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Liu ZhuangFei on 2018/2/27.
 */

public class BitmapUtils {
    // 等比缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);

        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap zoomAdapter(Bitmap bm, int newWidth){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scale = 1;
        if(width>(newWidth/3)){
            scale=((float) newWidth) / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scale,scale);

            Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            return newbm;
        }
        return bm;
    }
}
