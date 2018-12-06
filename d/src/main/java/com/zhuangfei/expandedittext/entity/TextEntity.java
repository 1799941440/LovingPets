package com.zhuangfei.expandedittext.entity;

import android.widget.TextView;

/**
 * Created by Liu ZhuangFei on 2018/2/28.
 */

public class TextEntity extends BaseEntity {

    private TextView textView;

    @Override
    public String getText() {
        return textView.getText().toString();
    }

    @Override
    public int getType() {
        return EntityType.TYPE_TEXT;
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public TextEntity(TextView textView) {
        this.textView = textView;
    }
}
