package com.zhuangfei.expandedittext.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zhuangfei.expandedittext.R;

/**
 * 使用Glide来加载图片
 * Created by Liu ZhuangFei on 2018/3/1.
 */

public class GlideImageLoader extends DefaultImageLoader {
    public GlideImageLoader(Context context) {
        super(context);
    }

    @Override
    public void setImageView(final ImageView imageView, String replace, final int width) {
        Glide.with(getContext()).asBitmap().load(replace).apply(new RequestOptions().placeholder(R.drawable.ic_holder))
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        para.width = width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
