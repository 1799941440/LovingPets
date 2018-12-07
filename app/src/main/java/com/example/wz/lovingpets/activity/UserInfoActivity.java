package com.example.wz.lovingpets.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.QiNiuUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.SingleDialog;
import com.example.wz.lovingpets.widget.SingleDialogBuilder;

import java.io.File;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.utils.StringUtils.formatUrl;

public class UserInfoActivity extends BaseFragmentActivity implements View.OnClickListener {

    private User user;
    private int userId;
    private Button bt_exit;
    private EditText et_name,et_age;
    private ImageView iv_back,iv_head;
    private boolean isEditting = false;
    private TextView tv_update,tv_balance,tv_phone,tv_sex,tv_title;
    private HttpRequest.ApiService api = HttpRequest.getApiservice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        iv_back = findViewById(R.id.titlebar_iv_left);
        tv_title = findViewById(R.id.titlebar_tv_title);
        iv_head = findViewById(R.id.user_info_head);
        tv_update = findViewById(R.id.user_info_update);
        tv_balance = findViewById(R.id.user_info_balance);
        tv_phone = findViewById(R.id.user_info_phone);
        tv_sex = findViewById(R.id.user_info_sex);
        et_name = findViewById(R.id.user_info_name);
        bt_exit = findViewById(R.id.user_info_exit);
        et_age = findViewById(R.id.user_info_age);
    }

    @Override
    protected void initData() {
        userId = new UserUtil(this).getUser().getId();
        iv_back.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        tv_title.setText("个人信息");
        et_name.setFocusable(false);
        et_name.setFocusableInTouchMode(false);
        et_age.setFocusable(false);
        et_age.setFocusableInTouchMode(false);
        tv_sex.setClickable(false);
        iv_head.setClickable(false);
        getUserInfo();
    }

    private void getUserInfo(){
        Observable<ListResponse<User>> observable = api.selectById(userId);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<User>>() {
            @Override
            public void onSuccess(ListResponse<User> listResponse) {
                if(listResponse.isSuccess()){
                    user = listResponse.getRows().get(0);
                    setUserData();
                }
            }
        });
    }

    private void setUserData() {
        ImageUtil.loadRoundImage(iv_head,user.getIcon());
        et_name.setText(user.getUserName());
        et_age.setText(user.getAge()+"");
        tv_sex.setText(user.getSex());
        tv_balance.setText(user.getBalance()+"");
        tv_phone.setText(user.getPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.user_info_update:
                if(isEditting){
                    saveInfo();
                    tv_update.setText("修改信息");
                    isEditting = false;
                    et_name.setFocusable(false);
                    et_name.setFocusableInTouchMode(false);
                    et_age.setFocusable(false);
                    et_age.setFocusableInTouchMode(false);
                    tv_sex.setClickable(false);
                    iv_head.setClickable(false);
                }else{
                    isEditting = true;
                    tv_update.setText("保存修改");
                    et_name.setFocusableInTouchMode(true);
                    et_name.setFocusable(true);
                    et_age.setFocusableInTouchMode(true);
                    et_age.setFocusable(true);
                    tv_sex.setClickable(true);
                    iv_head.setClickable(true);
                }
                break;
            case R.id.user_info_head:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 300);
                break;
            case R.id.user_info_sex:
                showSexDialog();
                break;
            case R.id.user_info_exit:
                new UserUtil(UserInfoActivity.this).cleanUser();
                intent2Activity(LaunchActivity.class);
                ActivityManager.getInstance().removeAll();
                break;
        }
    }

    private void saveInfo() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("正在修改中");
        pd.show();
        Observable<ListResponse> observable = api.updateUser(userId
                ,user.getIcon()
                ,et_name.getText().toString().trim()
                ,Integer.valueOf(et_age.getText().toString().trim())
                ,tv_sex.getText().toString());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    pd.dismiss();
                    showLongToast("修改用户信息成功");
                    new UserUtil(UserInfoActivity.this).saveUser(user);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == -1 && data != null){
            try {
                Uri uri = data.getData();
                Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                File head = new File(formatUrl(uri));
//                com.orhanobut.logger.Logger.i(uri.toString().substring(36));
//                /storage/emulated/0/PDFfiles/test.pdf
                startUpload(head);
                ImageUtil.loadRoundImage(iv_head,uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startUpload(File head) {
        tv_update.setClickable(false);
        QiNiuUtil qiNiuUtil = new QiNiuUtil();
        qiNiuUtil.startUpload(head,UserInfoActivity.this,0,userId);
        qiNiuUtil.setQiNiuCloudEvent(new QiNiuUtil.QiNiuCloudEvent() {
            @Override
            public void getUrlKey(String url) {
                user.setIcon(url);
                tv_update.setClickable(true);
            }
        });
    }

    private void showSexDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("设置性别").setList(Constant.list_user_sex).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_sex.setText(target);
                user.setSex(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }
}
