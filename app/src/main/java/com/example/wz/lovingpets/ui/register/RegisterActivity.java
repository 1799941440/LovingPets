package com.example.wz.lovingpets.ui.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MainActivity;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseContract;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.ui.login.LoginActivity;
import com.example.wz.lovingpets.ui.login.LoginPresenter;
import com.example.wz.lovingpets.utils.AnimUtils;
import com.example.wz.lovingpets.utils.Md5Util;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.utils.UserUtil;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements RegisterContract.View
        , View.OnClickListener,View.OnFocusChangeListener{

    private String phoneNum;
    private Button bt_getcode;
    private float mWidth, mHeight;
    private ObjectAnimator animator3;
    public boolean isregisting = false;
    private RegisterPresenter presenter;
    private EditText et_un,et_pw,et_code;
    private TextView tv_register;//注册按钮
    private View rl_onregister,mInputLayout;
    private ImageView iv_failed, iv_success;
    private RelativeLayout rl_un,rl_pw,rl_code;
    private RelativeLayout rl_failed, rl_success;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    startInputAnim(mInputLayout, mWidth, mHeight);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SMSSDK.registerEventHandler(eh);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        rl_un = findViewById(R.id.input_rl_un);//用户名输入框外层的rl，
        rl_pw = findViewById(R.id.input_rl_pw);//密码输入框外层的rl，
        rl_code = findViewById(R.id.input_rl_code);//验证码输入框外层的rl，
        et_un = findViewById(R.id.register_et_un);//用户名输入框
        et_pw = findViewById(R.id.register_et_pw);//密码输入框
        et_code = findViewById(R.id.register_et_code);//验证码输入框
        bt_getcode = findViewById(R.id.register_bt_getcode);//获取验证码按钮
        tv_register = findViewById(R.id.register_tv_register);//注册按钮
        mInputLayout = findViewById(R.id.register_input_layout);//伸缩变换的输入框根布局
        rl_onregister = findViewById(R.id.register_layout_progress);//转圈圈的
        rl_failed = findViewById(R.id.register_layout_failed);//注册失败的布局
        rl_success = findViewById(R.id.register_layout_success);//注册成功的布局
        iv_failed = findViewById(R.id.failed_iv);//注册失败的布局的叉叉
        iv_success = findViewById(R.id.success_iv);//注册失败的布局的√
    }

    @Override
    protected void initData() {
        presenter = new RegisterPresenter(this, HttpRequest.getApiservice());

        et_un.setOnFocusChangeListener(this);
        et_pw.setOnFocusChangeListener(this);
        et_code.setOnFocusChangeListener(this);

        bt_getcode.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_bt_getcode:
                getSecurity();
                break;
            case R.id.register_tv_register:
                if (StringUtils.isEmpty(et_un.getText().toString().trim())) {
                    showToast("请输入用户名");
                    return;
                }
                if (StringUtils.isEmpty(et_pw.getText().toString().trim())) {
                    showToast("请输入密码");
                    return;
                }
                if (StringUtils.isEmpty(et_code.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                testSecurity();
                break;
            default:break;
        }
    }

    //输入框边框的动画
    private void startInputAnim(final View view, float w, float h) {

        AnimatorSet set = AnimUtils.getInputAnim(view, w, 0);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rl_onregister.setVisibility(View.VISIBLE);
                loginingAnimator(rl_onregister);
                mInputLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    //登录中转圈的动画
    private void loginingAnimator(final View view) {
        animator3 = AnimUtils.getLoginingAnim(animator3, view);
        animator3.start();
        animator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                animation.cancel();
                presenter.register(phoneNum,et_pw.getText().toString().trim());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
    }

    @Override
    public void registerSuccess(User user, boolean success) {
        isregisting = false;
        rl_onregister.setVisibility(View.INVISIBLE);
        if (success) {
            new UserUtil(this).saveUser(user);
            showSuccessAnim();
        } else {
            showFailedAnim();
        }
    }

    private void showFailedAnim() {
        rl_failed.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.bounce_anim);
        iv_failed.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showToast("失败");
                rl_failed.setVisibility(View.INVISIBLE);
                reshowInputAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //登录失败后重新显示输入框的动画
    private void reshowInputAnim(){
        mInputLayout.setVisibility(View.VISIBLE);
        AnimatorSet set = AnimUtils.getInputAnim(mInputLayout, mWidth, 1);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rl_un.setVisibility(View.VISIBLE);
                rl_pw.setVisibility(View.VISIBLE);
                rl_code.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showSuccessAnim() {
        rl_success.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.bounce_anim);
        iv_success.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                intent2Activity(MainActivity.class);
                RegisterActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.register_et_un:
                if (hasFocus) {
                    rl_un.setActivated(true);
                    rl_pw.setActivated(false);
                    rl_code.setActivated(false);
                }
                break;
            case R.id.register_et_pw:
                if (hasFocus) {
                    rl_un.setActivated(false);
                    rl_pw.setActivated(true);
                    rl_code.setActivated(false);
                }
                break;
            case R.id.register_et_code:
                if (hasFocus) {
                    rl_un.setActivated(false);
                    rl_pw.setActivated(false);
                    rl_code.setActivated(true);
                    bt_getcode.setActivated(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(RegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            //If token is null, all callbacks and messages will be removed.
            handler.removeCallbacksAndMessages(null);
        }
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    public void getSecurity() {
        phoneNum = et_un.getText().toString().trim();
        //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否侧不能使用
        if (StringUtils.isMobileNO(phoneNum)) {
            showToast("号码不符合格式！");
        } else {
            SMSSDK.getVerificationCode("+86", phoneNum);
        }
    }

    public void testSecurity() {
        String security = et_code.getText().toString();
        if (!TextUtils.isEmpty(security)) {
            //提交短信验证码
            SMSSDK.submitVerificationCode("+86", phoneNum, security);//国家号，手机号码，验证码
        } else {
            showToast("验证码不能为空");
        }
    }

    private EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {//提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap<number,code>
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    HashMap<String, Object> mData = (HashMap<String, Object>) data;
                    String country = (String) mData.get("country");//返回的国家编号
                    String phone = (String) mData.get("phone");//返回用户注册的手机号

                    if (phone.equals(phoneNum)) {
                        runOnUiThread(new Runnable() {//更改ui的操作要放在主线程，实际可以发送hander
                            @Override
                            public void run() {
                                showToast("通过验证");
                                mWidth = tv_register.getMeasuredWidth();
                                mHeight = tv_register.getMeasuredHeight();
                                rl_un.setVisibility(View.INVISIBLE);
                                rl_pw.setVisibility(View.INVISIBLE);
                                rl_code.setVisibility(View.INVISIBLE);
                                handler.sendEmptyMessage(1);
                                isregisting = true;
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("验证失败1");
                            }
                        });
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("成功发出验证，请查看短信获取验证码");
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("验证失败0");
                    }
                });
                ((Throwable) data).printStackTrace();
            }
        }
    };
}
