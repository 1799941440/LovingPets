package com.example.wz.lovingpets.ui.goods_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.widget.PriceView;

public class GoodsListActivity extends BaseActivity {

    private PriceView p;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list1);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        p = findViewById(R.id.price);
        b = findViewById(R.id.bt);
    }

    @Override
    protected void initData() {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setPrice(189.9f);
                showToast("ejjnj");
            }
        });
    }
}
