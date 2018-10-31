package com.example.wz.lovingpets.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.entity.PetInfo;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.widget.SingleDialog;
import com.example.wz.lovingpets.widget.SingleDialogBuilder;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddPetActivity extends BaseFragmentActivity implements View.OnClickListener {

    private Button bt_save;
    private EditText et_name;
    private ImageView iv_head;
    private DatePickerDialog dp;
    private int seletedClass = 0;
    private PetInfo data = new PetInfo();
    private TextView title,tv_class,tv_state,tv_family,tv_sex,tv_birthday;
    private String headUrl = "http://ozfwev5ji.bkt.clouddn.com/mine_head_logined.png";
    private HttpRequest.ApiService api = HttpRequest.getApiservice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        title = findViewById(R.id.titlebar_tv_title);
        et_name = findViewById(R.id.editPet_name);
        iv_head = findViewById(R.id.editPet_head);
        bt_save = findViewById(R.id.editPet_save);
        tv_class = findViewById(R.id.editPet_family);
        tv_family = findViewById(R.id.editPet_class);
        tv_state = findViewById(R.id.editPet_state);
        tv_sex = findViewById(R.id.editPet_sex);
        tv_birthday = findViewById(R.id.editPet_birthday);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        if(b != null){
            data = new Gson().fromJson(b.getString("petInfo"),PetInfo.class);
        }
        if(data.getNickName()!=null){
            title.setText("修改宠物资料");
            setData(data);
        }else{
            title.setText("添加宠物");
            ImageUtil.loadRoundImage(iv_head,headUrl);
        }
        Calendar c = Calendar.getInstance();
        dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_birthday.setText(year+"-"+month+"-"+dayOfMonth);
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        tv_sex.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_class.setOnClickListener(this);
        tv_family.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
    }

    private void setData(PetInfo data) {
        ImageUtil.loadRoundImage(iv_head,data.getIcon());
        et_name.setText(data.getNickName());
        tv_class.setText(data.getClassName());
        tv_family.setText(data.getFamily());
        tv_sex.setText(data.getSex());
        tv_state.setText(data.getState());
        tv_birthday.setText(DateUtil.DateToString(data.getBirthday()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editPet_sex:
                showSexDialog();break;
            case R.id.editPet_state:
                showStateDialog();break;
            case R.id.editPet_class:
                showClassDialog();break;
            case R.id.editPet_family:
                showFamilyDialog();break;
            case R.id.editPet_birthday:
                dp.show();break;
        }
    }

    private void showFamilyDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("选择小类").setList(Constant.list_family.get(seletedClass)).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_class.setText(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showClassDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("选择大类").setList(Constant.list_class).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_family.setText(target);
                seletedClass = position;
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showStateDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("设置状态").setList(Constant.list_state).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_state.setText(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showSexDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("设置性别").setList(Constant.list_sex).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_sex.setText(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }
}
