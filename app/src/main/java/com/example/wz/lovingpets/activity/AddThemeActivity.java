package com.example.wz.lovingpets.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.Theme;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.FileTools;
import com.example.wz.lovingpets.utils.ImageTools;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhuangfei.expandedittext.ExpandEditText;

import io.reactivex.Observable;

public class AddThemeActivity extends BaseActivity implements View.OnClickListener {

    private String icon,name;
    private int userId,petId;
    private EditText et_title;
    private ExpandEditText eet;
    private LinearLayout ll_selectPet;
    private TextView tv_push,tv_petName;
    private ImageView iv_bavk,iv_petIcon;
    private Button bt_selectPic,bt_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theme);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        tv_push = findViewById(R.id.add_theme_push);
        iv_bavk = findViewById(R.id.add_theme_back);
        eet = findViewById(R.id.add_theme_eet);
        bt_selectPic = findViewById(R.id.add_theme_selectPic);
        bt_clear = findViewById(R.id.add_theme_clear);
        et_title = findViewById(R.id.add_theme_title);
        ll_selectPet = findViewById(R.id.add_theme_selectPet);
        iv_petIcon = findViewById(R.id.add_theme_petIcon);
        tv_petName = findViewById(R.id.add_theme_petName);
    }

    @Override
    protected void initData() {
        userId = new UserUtil(this).getUser().getId();
        tv_push.setOnClickListener(this);
        iv_bavk.setOnClickListener(this);
        bt_selectPic.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        ll_selectPet.setOnClickListener(this);
        eet.bind(this).setHintText("填写内容，1-2000字");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_theme_push:
                pushTheme();
                break;
            case R.id.add_theme_back:
                finish();
                break;
            case R.id.add_theme_selectPic:
                FileTools.chooseFiles(AddThemeActivity.this,1);
                break;
            case R.id.add_theme_clear:
                eet.clear();
                eet.createEditEntity(0);
                break;
            case R.id.add_theme_selectPet:
                Intent intent = new Intent(AddThemeActivity.this,MyPetsActivity.class);
                intent.putExtra("from",1);
                startActivityForResult(intent,0x123);
                break;
        }
    }

    private void pushTheme() {
        if (et_title.getText().toString().trim().length() == 0){
            showLongToast("请填写标题");
            return;
        }
        if(eet.getText().length() == 0){
            showLongToast("请填写内容");
            return;
        }
        Theme theme = new Theme();
        theme.setTitle(et_title.getText().toString().trim());
        theme.setContent(eet.getText());
        theme.setOwnerId(userId);
        theme.setPetId(petId);
        Observable<ListResponse> observable = HttpRequest.getApiservice().pushTheme(
            new Gson().toJson(theme)
        );
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("发布主题成功");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {// 取消
            return;
        }
        switch (requestCode) {
            case 1:
                Uri uri = intent.getData();
                String path = ImageTools.getPathFromUri(AddThemeActivity.this, uri);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                eet.insertBitmap(bitmap,path);
                break;

            case 0x123:
                petId = intent.getIntExtra("petId",0);
                icon = intent.getStringExtra("icon");
                name = intent.getStringExtra("name");
                setPetInfo();
                break;

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void setPetInfo() {
        ImageUtil.loadNetImage(iv_petIcon,icon);
        tv_petName.setText(name);
    }
}
