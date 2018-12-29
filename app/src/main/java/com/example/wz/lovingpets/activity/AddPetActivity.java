package com.example.wz.lovingpets.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.PetInfo;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.QiNiuUtil;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.SingleDialog;
import com.example.wz.lovingpets.widget.SingleDialogBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;

import io.reactivex.Observable;

public class AddPetActivity extends BaseFragmentActivity implements View.OnClickListener {

    private int userId;
    private Button bt_save;
    private EditText et_name;
    private ImageView iv_head;
    private DatePickerDialog dp;
    private int seletedClass = 145,selectedFamily=0;
    private PetInfo data = new PetInfo();
    private TextView title,tv_class,tv_state,tv_family,tv_sex,tv_birthday;
    private String headUrl = "http://a.hiphotos.baidu.com/image/%70%69%63/item/3bf33a87e950352aa665e2ba5e43fbf2b2118b78.jpg";
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
        userId = new UserUtil(this).getUser().getId();
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
            data = new PetInfo(0,userId,"","正常","公",
                    DateUtil.stringToDate("2016-1-1"),headUrl,6,145);
            ImageUtil.loadRoundImage(iv_head,headUrl);
        }
        Calendar c = Calendar.getInstance();
        dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_birthday.setText(year+"-"+(month+1)+"-"+dayOfMonth);
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        iv_head.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_class.setOnClickListener(this);
        tv_family.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        bt_save.setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == -1 && data != null){
            try {
                /**
                 * 该uri是上一个Activity返回的
                 */
                Uri uri = data.getData();
                Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                File head = new File(StringUtils.formatUrl(uri));
//                com.orhanobut.logger.Logger.i(uri.toString().substring(36));
//                /storage/emulated/0/PDFfiles/test.pdf
                startUpload(head);
                ImageUtil.loadRoundImage(iv_head,uri);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("tag",e.getMessage());
                Toast.makeText(this,"程序崩溃", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startUpload(File head) {
        bt_save.setClickable(false);
        QiNiuUtil qiNiuUtil = new QiNiuUtil();
        qiNiuUtil.startUpload(head,AddPetActivity.this,2,userId);
        qiNiuUtil.setQiNiuCloudEvent(new QiNiuUtil.QiNiuCloudEvent() {
            @Override
            public void getUrlKey(String url) {
                data.setIcon(url);
                bt_save.setClickable(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editPet_head:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 300);break;
            case R.id.editPet_sex:
                showSexDialog();break;
            case R.id.editPet_state:
                showStateDialog();break;
            case R.id.editPet_class:
                showClassDialog();break;
            case R.id.editPet_family:
                showFamilyDialog();break;
            case R.id.editPet_save:
                savePetInfo();break;
            case R.id.editPet_birthday:
                dp.show();break;
        }
    }

    private void savePetInfo() {
        if(StringUtils.isEmpty(et_name.getText().toString().trim())){
            showToast("请将信息填写完整");
            return;
        }
        data.setNickName(et_name.getText().toString().trim());
        addPet(data);
        EventBusUtils.sendEvent(new Event(EventCodes.MANAGE_PET,null));
    }

    private void addPet(PetInfo data) {
        Observable<ListResponse> observable = api.managePet(data.getId(),data.getNickName(),
        data.getFamilyId(),data.getUserId(),data.getClassId(),data.getState(),data.getSex(),data.getIcon(),tv_birthday.getText().toString());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showToast(listResponse.getMsg());
                    finish();
                }
            }
        });
    }

    private void showFamilyDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("选择小类").setList(Constant.list_family.get(selectedFamily)).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_class.setText(target);
                if(selectedFamily == 0){
                    data.setClassId(position+1);
                }else{
                    data.setClassId(position+48);
                }
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
                selectedFamily = position;
                data.setFamilyId(position+1);
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
                data.setState(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showSexDialog() {
        SingleDialog dialog= new SingleDialogBuilder().setTitle("设置性别").setList(Constant.list_pet_sex).build();
        dialog.setCallBack(new SingleDialog.CallBack() {
            @Override
            public void onItemClick(String target,int position) {
                tv_sex.setText(target);
                data.setSex(target);
            }
        });
        dialog.show(getSupportFragmentManager());
    }
}
