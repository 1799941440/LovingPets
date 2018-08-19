package com.example.wz.lovingpets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.ywp.addresspickerlib.AddressPickerView;

public class MyPetsActivity extends BaseActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        tv = findViewById(R.id.mypets_tv);
    }

    @Override
    protected void initData() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressPickerPop();
            }
        });
    }

    private void showAddressPickerPop() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv.setText(address);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAsDropDown(tv);
    }
}
