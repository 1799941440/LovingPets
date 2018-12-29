package com.example.wz.lovingpets.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;
import com.ywp.addresspickerlib.AddressPickerView;

import io.reactivex.Observable;

public class ApplyActivity extends BaseActivity implements View.OnClickListener{

    private PopupWindow popupWindow;
    private TextView tv_address,tv_title;
    private ImageView iv_back;
    private EditText et_shopName,et_fullAddress;
    private View pop_top;
    private Button bt_commit;
    private Integer userId;
    private HttpRequest.ApiService api = HttpRequest.getApiservice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        tv_address = findViewById(R.id.apply_address);
        tv_title = findViewById(R.id.titlebar_tv_title);
        tv_address = findViewById(R.id.apply_address);
        pop_top = findViewById(R.id.apply_pop_top);
        iv_back = findViewById(R.id.titlebar_iv_left);
        et_shopName = findViewById(R.id.apply_shopName);
        et_fullAddress = findViewById(R.id.apply_full_address);
        bt_commit = findViewById(R.id.apply_commit);
    }

    @Override
    protected void initData() {
        popupWindow = new PopupWindow(this);
        tv_title.setText(R.string.apply_shop);
        userId = new UserUtil(this).getUser().getId();
        tv_address.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        initPopwindow();
    }

    private void initPopwindow() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv_address.setText(address.trim().replace(" ",""));
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popwin_bg));
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apply_address:
                popupWindow.showAsDropDown(pop_top);
                break;
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.apply_commit:
                commit();
                break;
        }
    }

    private void commit() {
        String shopName = et_shopName.getText().toString().trim();
        String fullAddress = et_fullAddress.getText().toString().trim();
        String address = tv_address.getText().toString().trim();
        Observable<ListResponse> observable = api.toBeShopKeeper(userId,shopName,address+fullAddress);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("申请成功，请耐心等待");
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }else {
            finish();
        }
    }
}
