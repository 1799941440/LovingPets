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
import com.example.wz.lovingpets.entity.ServiceComment;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

public class CommentServerActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_commit;
    private EditText et_comment;
    private String imageUrl;
    private TextView tv_des,tv_title;
    private byte stars = 5;
    private int userId,serviceId;
    private ImageView iv_back,iv_image,iv_star1,iv_star2,iv_star3,iv_star4,iv_star5;
    private List<ImageView> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_server);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        tv_title = findViewById(R.id.titlebar_tv_title);
        iv_back = findViewById(R.id.titlebar_iv_left);
        iv_image = findViewById(R.id.comment_server_image);
        iv_star1 = findViewById(R.id.comment_server_star1);
        iv_star2 = findViewById(R.id.comment_server_star2);
        iv_star3 = findViewById(R.id.comment_server_star3);
        iv_star4 = findViewById(R.id.comment_server_star4);
        iv_star5 = findViewById(R.id.comment_server_star5);
        tv_des = findViewById(R.id.comment_server_des);
        et_comment = findViewById(R.id.comment_server_comment);
        bt_commit = findViewById(R.id.comment_server_commit);
    }

    @Override
    protected void initData() {
        userId = new UserUtil(this).getUser().getId();
        list.addAll(Arrays.asList(iv_star1,iv_star2,iv_star3,iv_star4,iv_star5));
        serviceId = getIntent().getIntExtra("serviceId",0);
        imageUrl = getIntent().getStringExtra("imageUrl");
        ImageUtil.loadNetImage(iv_image,imageUrl);
        iv_back.setOnClickListener(this);
        iv_star1.setOnClickListener(this);
        iv_star2.setOnClickListener(this);
        iv_star3.setOnClickListener(this);
        iv_star4.setOnClickListener(this);
        iv_star5.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.comment_server_star1:
                onStarClick((byte)1);
                break;
            case R.id.comment_server_star2:
                onStarClick((byte)2);
                break;
            case R.id.comment_server_star3:
                onStarClick((byte)3);
                break;
            case R.id.comment_server_star4:
                onStarClick((byte)4);
                break;
            case R.id.comment_server_star5:
                onStarClick((byte)5);
                break;
            case R.id.comment_server_commit:
                commitComment();
                break;
        }
    }

    private void commitComment() {
        ServiceComment sc = new ServiceComment();
        sc.setOrderId(serviceId);
        sc.setUserId(userId);
        sc.setCommentTime(new Date());
        sc.setStar(stars);
        sc.setComment(et_comment.getText().toString().trim());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Observable<ListResponse> observable = HttpRequest.getApiservice().comentServiceOrder(
                gson.toJson(sc)
        );
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("评论成功");
                    finish();
                }
            }
        });
    }

    private void onStarClick(byte star){
        for(int i = 0; i <= star-1; i++){
            list.get(i).setImageResource(R.drawable.ic_collected);
        }
        for(int j = 4; j > star-1; j--){
            list.get(j).setImageResource(R.drawable.ic_my_collection);
        }
        if(star == 1){
            tv_des.setText("非常差");
        }else if(star == 2){
            tv_des.setText("差");
        }else if(star == 3){
            tv_des.setText("一般");
        }else if(star == 4){
            tv_des.setText("好");
        }else if(star == 5){
            tv_des.setText("非常好");
        }
        stars = star;
    }
}
