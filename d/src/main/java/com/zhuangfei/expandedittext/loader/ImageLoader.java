package com.zhuangfei.expandedittext.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhuangfei.expandedittext.wrapper.ImageWrapper;

/**
 * Created by Liu ZhuangFei on 2018/3/1.
 */

public abstract class ImageLoader {

    private Context context;

    public ImageLoader(Context context){
        this.context=context;
    }

    public Context getContext(){
        return context;
    }

    public abstract View getView(LayoutInflater mInflate);

    public abstract ImageView getImageView(View view);

    public abstract void setImageView(ImageView imageView,String replace,int width);
}
