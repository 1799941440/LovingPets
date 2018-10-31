package com.example.wz.lovingpets.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtil {
    public static void loadRoundImage(ImageView view, String url) {

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
                //圆形
                .circleCrop();
        Glide.with(view.getContext())
                .load(url).apply(options).into(view);
    }

    public static void loadNetImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url).into(view);
    }

    public static void loadLocalImage(ImageView view, int url) {
        Glide.with(view.getContext())
                .load(url).into(view);
    }
}
