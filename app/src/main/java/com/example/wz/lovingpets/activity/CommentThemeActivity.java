package com.example.wz.lovingpets.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.net.HttpRequest;

import io.reactivex.Observable;

public class CommentThemeActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_commit;
    private ImageView iv_back;
    private TextView tv_title;
    private int userId,themeId;
    private EditText et_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_theme);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        et_comment = findViewById(R.id.comment_theme_comment);
        iv_back = findViewById(R.id.titlebar_iv_left);
        tv_title = findViewById(R.id.titlebar_tv_title);
        bt_commit = findViewById(R.id.comment_theme_commit);
    }

    @Override
    protected void initData() {
        Bundle b = getIntent().getBundleExtra("bundle");
        if(b != null){
            userId = b.getInt("userId");
            themeId = b.getInt("themeId");
            tv_title.setText("发布评论");
            bt_commit.setOnClickListener(this);
            iv_back.setOnClickListener(this);
        }else{
            showLongToast("非法跳转");
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.comment_theme_commit:
                addThemeComment();
                break;
        }
    }

    private void addThemeComment() {
        if(et_comment.getText().toString().trim().length()<=0){
            showLongToast("你还没有填写评论哦");
            return;
        }
        Observable<ListResponse> observable = HttpRequest.getApiservice().commentTheme(
                themeId, et_comment.getText().toString().trim(),userId
        );
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    finish();
                    showLongToast("评论成功");
                }
            }
        });
    }
}
