package com.example.wz.lovingpets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.Address;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ywp.addresspickerlib.AddressPickerView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 编辑、添加地址
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener{

    private User user;
    private View pop_top;
    private Address data;
    private Switch aSwitch;
    private Button bt_save;
    private TextView title,tv_address;
    private PopupWindow popupWindow;
    private EditText et_receiver,et_contact,et_fullAddress;
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        title = findViewById(R.id.titlebar_tv_title);
        et_receiver = findViewById(R.id.editAddress_receiver);
        et_contact = findViewById(R.id.editAddress_contact);
        tv_address = findViewById(R.id.editAddress_address);
        et_fullAddress = findViewById(R.id.editAddress_fullAddress);
        bt_save = findViewById(R.id.editAddress_save);
        pop_top = findViewById(R.id.pop_top);
        aSwitch = findViewById(R.id.editAddress_switch);
    }

    /**
     * 初始化跳转的数据、标题栏、popwindow、监听器
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        user = new UserUtil(this).getUser();
        data = new Gson().fromJson(intent.getStringExtra("address"),Address.class);
        if(data == null){
            title.setText("添加收货地址");
            data = new Address();
        }else{
            title.setText("编辑收货地址");
            setData(data);
        }
        popupWindow = new PopupWindow(this);
        initPopwindow();
        tv_address.setOnClickListener(this);
        bt_save.setOnClickListener(this);
    }

    /**
     * 为界面设置数据
     * @param address
     */
    private void setData(Address address){
        et_receiver.setText(address.getReceiver());
        et_contact.setText(address.getContact());
        tv_address.setText(appendAddress(address));
        et_fullAddress.setText(getFullAddress(address));
        aSwitch.setChecked(address.getIsCommonAddress() == 1);
    }

    private void checkAndSaveAddress() {
        int commonAddress;
        String receiver,contact,province,city,fullAddress;
        String[] a = tv_address.getText().toString().trim().split("-");
        if(a.length<2){
            showToast("请将地区填写完整");
            return;
        }
        commonAddress = aSwitch.isChecked()?1:0;
        receiver = et_receiver.getText().toString().trim();
        contact = et_contact.getText().toString().trim();
        province = a[0];
        city = a[1];
        fullAddress = a[2]+"-"+et_fullAddress.getText().toString().trim();
        if(StringUtils.checkStrings(receiver,contact,province,city,fullAddress)){
            showToast("请将信息填写完整");
            return;
        }else{
            saveAddress(receiver,contact,province,city,fullAddress,commonAddress);
        }
    }

    private void saveAddress(String receiver, String contact, String province, String city, String fullAddress,int commonAddress) {
        Observable<ListResponse> observable = api.manageAddress(data.getId() == null?0:data.getId(),user.getId(),receiver,contact,province,city,
                fullAddress,commonAddress);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showToast(listResponse.getMsg());
                    EventBusUtils.sendEvent(new Event(EventCodes.SAVE_ADDRESS,null));
                    finish();
                }
            }
        });
    }

    private String appendAddress(Address data) {
        String s = "";
        String[] temp;
        s+=(data.getProvince()+"-");
        s+=(data.getCity()+"-");
        temp = data.getFullAddress().split("-");
        if(temp.length!=0){
            s+=(temp[0]);
        }
        return s;
    }

    private String getFullAddress(Address data) {
        String result = "";
        String[] temp;
        temp = data.getFullAddress().split("-");
        if(temp.length>0){
            result+=(temp[1]);
        }
        return result;
    }

    //初始化地址选择框
    private void initPopwindow() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv_address.setText(address.trim().replace(" ","-"));
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popwin_bg));
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onBackPressed() {
        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editAddress_save: //保存按钮
                checkAndSaveAddress();break;
            case R.id.editAddress_address: //点击选择地址
                if(!popupWindow.isShowing()){
                    popupWindow.showAsDropDown(pop_top);
                }break;
            default:
        }
    }
}
