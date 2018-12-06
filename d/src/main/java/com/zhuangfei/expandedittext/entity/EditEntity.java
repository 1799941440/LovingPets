package com.zhuangfei.expandedittext.entity;

import android.widget.EditText;

/**
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class EditEntity extends BaseEntity{
    EditText editText;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public EditEntity(EditText editText) {
        this.editText = editText;
    }

    public String getText() {
        return editText==null?"":editText.getText().toString();
    }

    @Override
    public int getType() {
        return EntityType.TYPE_EDIT;
    }

    public void setText(String text) {
        getEditText().setText(text);
    }
}
