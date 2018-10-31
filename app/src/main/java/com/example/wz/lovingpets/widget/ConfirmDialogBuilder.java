package com.example.wz.lovingpets.widget;

import android.graphics.Color;

public class ConfirmDialogBuilder {
    public int text_color_title, text_color_left, text_color_right,
            bg_color_title, bg_color_left, bg_color_right;
    private String tv_title,tv_content,tv_left,tv_right;

    public ViewDialogFragment build(){
        return new ViewDialogFragment(this);
    }

    public int getText_color_title() {
        return text_color_title;
    }

    public ConfirmDialogBuilder setText_color_title(String text_color_title) {
        this.text_color_title = Color.parseColor(text_color_title);
        return this;
    }

    public int getText_color_left() {
        return text_color_left;
    }

    public ConfirmDialogBuilder setText_color_left(String text_color_left) {
        this.text_color_left = Color.parseColor(text_color_left);
        return this;
    }

    public int getText_color_right() {
        return text_color_right;
    }

    public ConfirmDialogBuilder setText_color_right(String text_color_right) {
        this.text_color_right = Color.parseColor(text_color_right);
        return this;
    }

    public int getBg_color_title() {
        return bg_color_title;
    }

    public ConfirmDialogBuilder setBg_color_title(String bg_color_title) {
        this.bg_color_title = Color.parseColor(bg_color_title);
        return this;
    }

    public int getBg_color_left() {
        return bg_color_left;
    }

    public ConfirmDialogBuilder setBg_color_left(String bg_color_left) {
        this.bg_color_left = Color.parseColor(bg_color_left);
        return this;
    }

    public int getBg_color_right() {
        return bg_color_right;
    }

    public ConfirmDialogBuilder setBg_color_right(String bg_color_right) {
        this.bg_color_right = Color.parseColor(bg_color_right);
        return this;
    }

    public String getTv_title() {
        return tv_title;
    }

    public ConfirmDialogBuilder setTv_title(String tv_title) {
        this.tv_title = tv_title;
        return this;
    }

    public String getTv_content() {
        return tv_content;
    }

    public ConfirmDialogBuilder setTv_content(String tv_content) {
        this.tv_content = tv_content;
        return this;
    }

    public String getTv_left() {
        return tv_left;
    }

    public ConfirmDialogBuilder setTv_left(String tv_left) {
        this.tv_left = tv_left;
        return this;
    }

    public String getTv_right() {
        return tv_right;
    }

    public ConfirmDialogBuilder setTv_right(String tv_right) {
        this.tv_right = tv_right;
        return this;
    }
}