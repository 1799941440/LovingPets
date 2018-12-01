package com.example.wz.lovingpets.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MainActivity;
import com.example.wz.lovingpets.base.BaseContract;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.db.UserDao;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.ui.register.RegisterActivity;
import com.example.wz.lovingpets.utils.AnimUtils;
import com.example.wz.lovingpets.utils.GreenDaoManager;
import com.example.wz.lovingpets.utils.Md5Util;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.utils.UserUtil;

import org.greenrobot.eventbus.Subscribe;

@BindEventBus
public class LoginActivity extends Activity implements View.OnFocusChangeListener
        , LoginContract.LoginView, View.OnClickListener {

    private long exitTime;
    private Handler handler;
    private String from;
    private TextView mBtnLogin;//登录按钮
    private float mWidth, mHeight;//
    private EditText et_un, et_pw;
    private LoginPresenter presenter;
    private ObjectAnimator animator3;
    private View logining, mInputLayout;//
    private TextView tv_forgot, tv_goto_register;
    public boolean isActive = false, isLogining;
    private ImageView icon, ic_after, iv_failed, iv_success;//一开始显示的图标以及变化后的位置，成功以及失败图片
    private RelativeLayout rl_root, rl_un, rl_pw, rl_failed, rl_success;//input根布局、username、password、失败、成功的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_in, 0);//设置进入该activity的动画
        findViews();
        initData();
    }

    //全都是findviewbyid
    protected void findViews() {
        mBtnLogin = findViewById(R.id.main_bt_login);
        logining = findViewById(R.id.login_layout_progress);
        mInputLayout = findViewById(R.id.login_input_layout);
        rl_un = findViewById(R.id.input_rl_un);
        rl_pw = findViewById(R.id.input_rl_pw);
        et_un = findViewById(R.id.register_et_un);
        et_pw = findViewById(R.id.register_et_pw);
        icon = findViewById(R.id.login_icon);
        ic_after = findViewById(R.id.login_after_anim);
        rl_root = findViewById(R.id.login_rl_root);
        iv_failed = findViewById(R.id.failed_iv);
        iv_success = findViewById(R.id.success_iv);
        rl_failed = findViewById(R.id.login_layout_failed);
        rl_success = findViewById(R.id.login_layout_success);
        tv_forgot = findViewById(R.id.login_tv_forgot_pw);
        tv_goto_register = findViewById(R.id.login_tv_goto_register);
    }

    //为变量赋值、设置监听器等
    protected void initData() {
        presenter =  new LoginPresenter(this, HttpRequest.getApiservice());
        rl_root.setAlpha(0);
        //登录键点击后的动画
        mBtnLogin.setOnClickListener(this);
        et_un.setOnFocusChangeListener(this);
        et_pw.setOnFocusChangeListener(this);
        tv_forgot.setOnClickListener(this);
        tv_goto_register.setOnClickListener(this);
        from = getIntent().getStringExtra("from");
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //开始登录根布局的动画
                    case 0:
                        if (rl_root.getAlpha() == 1) {
                            rl_root.setAlpha(0);
                        }
                        startIconAnim();
                        break;
                    case 1:
                        startInputAnim(mInputLayout, mWidth, mHeight);
                        break;
                    default:
                        break;
                }
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 300);
    }

    public void showLoginAnim() {
        startInputAnim(mInputLayout, mWidth, mHeight);
    }

    @Override
    public void showTip(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
                logining.setVisibility(View.VISIBLE);
                loginingAnimator(logining);
                mInputLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    //登录失败后重新显示输入框的动画
    private void reshowInputAnim() {
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
                et_pw.setText("");
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
                presenter.login(et_un.getText().toString().trim(), Md5Util.md5(et_pw.getText().toString().trim()));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
    }

    //icon入场动画,完成动画后自动调用startRootAnim()
    protected void startIconAnim() {
        AnimatorSet logoAnim = AnimUtils.getIconIncomeAnim(this, ic_after, icon);
        logoAnim.start();
        logoAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //待ivLogo的动画结束后,开始播放底部注册、登录按钮的动画
                startRootAnim();
            }
        });
    }

    //login界面根布局的动画
    public void startRootAnim() {
        ObjectAnimator alphaRoot = ObjectAnimator.ofFloat(rl_root, "alpha", 0, 1);
        AnimatorSet rootAnim = new AnimatorSet();
        rootAnim.setDuration(1000);
        rootAnim.play(alphaRoot);
        rootAnim.start();
    }

    @Subscribe
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bt_login:
//                if (!StringUtils.isMobileNO(et_un.getText().toString().trim())) {
//                    showTip("请输入正确格式的手机号码");
//                    return;
//                }else
                if (StringUtils.isEmpty(et_un.getText().toString().trim())) {
                    showTip("请输入用户名");
                    return;
                }
                if (StringUtils.isEmpty(et_pw.getText().toString().trim())) {
                    showTip("请输入密码");
                    return;
                }
                if(isLogining){
                    return;
                }else{
                    mWidth = mBtnLogin.getMeasuredWidth();
                    mHeight = mBtnLogin.getMeasuredHeight();
                    rl_un.setVisibility(View.INVISIBLE);
                    rl_pw.setVisibility(View.INVISIBLE);
                    handler.sendEmptyMessage(1);
                    isLogining = true;
                }
                break;
            case R.id.login_tv_forgot_pw:
                break;

            case R.id.login_tv_goto_register:
                intent2Activity(RegisterActivity.class);
                break;
        }

    }

    //由p层实例调用该方法，参数为返回的用户信息和是否成功
    @Override
    public void loginSuccess(User user, boolean issuccess) {
        isLogining = false;
        logining.setVisibility(View.INVISIBLE);
        if (issuccess) {
            new UserUtil(this).saveUser(user);
            showSuccessAnim();
        } else {
            showFailedAnim();
        }
    }

    //登录失败显示叉叉，完成动画后调用reShow....方法重新显示登录框
    private void showFailedAnim() {
        rl_failed.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce_anim);
        iv_failed.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showTip("失败");
                rl_failed.setVisibility(View.INVISIBLE);
                reshowInputAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //登录成功，跳转进入主页
    private void showSuccessAnim() {
        rl_success.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce_anim);
        iv_success.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(from == null | "LAUNCH_ACTIVITY".equals(from)){
                    finish();
                }else{
                    intent2Activity(MainActivity.class);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //为输入框点击变色
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.register_et_un:
                if (hasFocus) {
                    rl_un.setActivated(true);
                    rl_pw.setActivated(false);
                }
                break;
            case R.id.register_et_pw:
                if (hasFocus) {
                    rl_un.setActivated(false);
                    rl_pw.setActivated(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
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
        presenter = null;
        super.onDestroy();
    }

    public void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isLogining) {
                //reshowInputAnim();
            } else {
                if (System.currentTimeMillis() - exitTime > 1500) {
                    showTip("再按一次退出爱宠APP");
                    exitTime = System.currentTimeMillis();
                } else {
                    LoginActivity.this.finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public UserDao getUserDao() {
        return GreenDaoManager.getInstance().getmDaoSession().getUserDao();
    }


    public User getUser(Long id) {
        return getUserDao().loadByRowId(id);
    }

}