package com.example.wz.lovingpets.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.utils.StringUtils;

public class ViewDialogFragment extends DialogFragment {

    private ConfirmDialogBuilder b;
    private TextView tv_title,tv_content;
    private Button bt_left, bt_right;
    private LayoutInflater inflater;

    public ViewDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public ViewDialogFragment(ConfirmDialogBuilder builder) {
        this.b = builder;
    }

    public interface Callback {
        void onLeftClick();
        void onRightClick();
    }

    private Callback callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "ViewDialogFragment");
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_comfirm, null);
        initView(view);
        initBuilder();
        builder.setView(view);
        return builder.create();
    }

    private void initBuilder() {
        if(!StringUtils.isEmpty(b.getTv_title())){
            tv_title.setText(b.getTv_title());
        }
        if(!StringUtils.isEmpty(b.getTv_content())){
            tv_content.setText(b.getTv_content());
        }
        if(!StringUtils.isEmpty(b.getTv_left())){
            bt_left.setText(b.getTv_left());
        }
        if(!StringUtils.isEmpty(b.getTv_right())){
            bt_right.setText(b.getTv_right());
        }
        if(!StringUtils.checkInt(b.getText_color_title())){
            tv_title.setTextColor(b.getText_color_title());
        }
        if(!StringUtils.checkInt(b.getBg_color_title())){
            tv_title.setBackgroundColor(b.getBg_color_title());
        }
        if(!StringUtils.checkInt(b.getText_color_left())){
            bt_left.setTextColor(b.getText_color_left());
        }
        if(!StringUtils.checkInt(b.getBg_color_left())){
            bt_left.setBackgroundColor(b.getBg_color_left());
        }
        if(!StringUtils.checkInt(b.getText_color_right())){
            bt_right.setTextColor(b.getText_color_right());
        }
        if(!StringUtils.checkInt(b.getBg_color_right())){
            bt_right.setBackgroundColor(b.getBg_color_right());
        }
        if(bt_left!=null){
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                        callback.onLeftClick();
                        dismiss();
                    }else{
                        throw new NullPointerException("You have not set a CallBack for this dialog");
                    }
                }
            });
        }
        if(bt_right!=null){
            bt_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                        callback.onRightClick();
                        dismiss();
                    }else{
                        throw new NullPointerException("You have not set a CallBack for this dialog");
                    }
                }
            });
        }
    }

    private void initView(View view) {
        tv_title = view.findViewById(R.id.dialog_tv_title);
        tv_content = view.findViewById(R.id.dialog_tv_content);
        bt_left = view.findViewById(R.id.dialog_tv_left);
        bt_right = view.findViewById(R.id.dialog_tv_right);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}