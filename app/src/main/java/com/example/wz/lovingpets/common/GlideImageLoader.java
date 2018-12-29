package com.example.wz.lovingpets.common;

import android.content.Context;
import android.widget.ImageView;

import com.example.wz.lovingpets.utils.ImageUtil;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageUtil.loadNetImage(imageView, (String) path);
    }
}
