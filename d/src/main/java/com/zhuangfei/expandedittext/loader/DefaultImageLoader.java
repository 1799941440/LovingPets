package com.zhuangfei.expandedittext.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhuangfei.expandedittext.R;
import com.zhuangfei.expandedittext.utils.BitmapUtils;

/**
 * 默认的图片加载者
 * Created by Liu ZhuangFei on 2018/3/1.
 */

public class DefaultImageLoader extends ImageLoader {

    public DefaultImageLoader(Context context) {
        super(context);
    }
    @Override
    public View getView(LayoutInflater mInflate) {
        View view = mInflate.inflate(R.layout.layout_imageview, null, false);
        return view;
    }

    @Override
    public ImageView getImageView(View view) {
        return view.findViewById(R.id.id_expand_imageview);
    }

    @Override
    public void setImageView(ImageView imageView, String replace, int width) {

    }
}
