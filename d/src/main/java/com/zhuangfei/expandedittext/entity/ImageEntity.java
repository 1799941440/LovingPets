package com.zhuangfei.expandedittext.entity;

import android.widget.ImageView;

/**
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class ImageEntity extends BaseEntity{

    private String text;

    private ImageView imageView;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getType() {
        return EntityType.TYPE_IMAGE;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageEntity(String text, ImageView imageView) {
        this.text = text;
        this.imageView = imageView;
    }
}
