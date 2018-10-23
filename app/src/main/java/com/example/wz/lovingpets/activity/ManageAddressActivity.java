package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.utils.StatusBarUtil;


public class ManageAddressActivity extends BaseActivity {

    private ImageView iv_add,iv_back;
    private TextView title;
    private RelativeLayout rl_titlebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        iv_add = findViewById(R.id.iv_titlebar_add);
        rl_titlebar = findViewById(R.id.rl_titlebar);
        title = findViewById(R.id.titlebar_tv_left);
    }

    @Override
    protected void initData() {
        title.setText("管理收货地址");
        int color = MyApp.getInstance().getCurrentColor();
        rl_titlebar.setBackgroundColor(color);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2Activity(EditAddressActivity.class);
            }
        });
    }
}
