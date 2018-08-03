package com.example.wz.lovingpets.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MainActivity;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.JellyInterpolator;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.Md5Util;
import com.example.wz.lovingpets.utils.UserUtil;

public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener, LoginContract.View {

    private TextView mBtnLogin;//登录按钮
    private View logining,mInputLayout;//
    private float mWidth, mHeight;//
    private RelativeLayout root, rl_un, rl_pw,rl_failed, rl_success;//input根布局、username、password、失败、成功的id
    private ImageView icon, ic_after,iv_failed, iv_success;//一开始显示的图标以及变化后的位置，成功以及失败图片
    private EditText et_un, et_pw;
    private Handler handler;
    private LoginContract.Presenter presenter;
    private ObjectAnimator animator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_in, 0);//设置进入该activity的动画
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        logining = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        rl_un = findViewById(R.id.input_rl_un);
        rl_pw = findViewById(R.id.input_rl_pw);
        et_un = findViewById(R.id.login_et_un);
        et_pw = findViewById(R.id.login_et_pw);
        icon = findViewById(R.id.login_icon);
        ic_after = findViewById(R.id.login_after_anim);
        root = findViewById(R.id.login_rl_root);
        iv_failed = findViewById(R.id.failed_iv);
        iv_success = findViewById(R.id.success_iv);
        rl_failed = findViewById(R.id.layout_failed);
        rl_success = findViewById(R.id.layout_success);
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenter(this, HttpRequest.getApiservice());
        root.setAlpha(0);
        //登录键点击后的动画
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWidth = mBtnLogin.getMeasuredWidth();
                mHeight = mBtnLogin.getMeasuredHeight();
                rl_un.setVisibility(View.INVISIBLE);
                rl_pw.setVisibility(View.INVISIBLE);
                handler.sendEmptyMessage(1);
            }
        });
        et_un.setOnFocusChangeListener(this);
        et_pw.setOnFocusChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //开始登录根布局的动画
                    case 0:
                        if (root.getAlpha() == 1) {
                            root.setAlpha(0);
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
        }, 500);
    }

    public void showLoginAnim() {
        startInputAnim(mInputLayout, mWidth, mHeight);
    }

    //输入框边框的动画
    private void startInputAnim(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
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

    private void reshowInputAnim(){
        AnimatorSet set = new AnimatorSet();
        mInputLayout.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofFloat(mWidth, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                mInputLayout.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 0.5f, 1f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
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
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setRepeatCount(10);
        animator3.setInterpolator(new JellyInterpolator());
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
        //获取屏幕高度
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        //通过测量，获取ivLogo的高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ic_after.measure(w, h);
        int logoHeight = ic_after.getMeasuredHeight();
        float transY = (screenHeight - logoHeight) * 0.3924f;
        ObjectAnimator tranLogo = ObjectAnimator.ofFloat(icon, "translationY", 0, -transY);
        ObjectAnimator scaleXLogo = ObjectAnimator.ofFloat(icon, "scaleX", 1f, 1f / 3);
        ObjectAnimator scaleYLogo = ObjectAnimator.ofFloat(icon, "scaleY", 1f, 1f / 3);
        AnimatorSet logoAnim = new AnimatorSet();
        logoAnim.setDuration(1000);
        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo);
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
        ObjectAnimator alphaRoot = ObjectAnimator.ofFloat(root, "alpha", 0, 1);
        final AnimatorSet rootAnim = new AnimatorSet();
        rootAnim.setDuration(1000);
        rootAnim.play(alphaRoot);
        rootAnim.start();
    }

    @Override
    public void loginSuccess(User user, boolean success) {
        if (success) {
            new UserUtil(this).saveUser(user);
            showSuccessAnim();
        } else {
            showFailedAnim();
        }
    }

    private void showFailedAnim() {
        logining.setVisibility(View.INVISIBLE);
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

    private void showSuccessAnim() {
        logining.setVisibility(View.INVISIBLE);
        rl_success.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce_anim);
        iv_success.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                intent2Activity(MainActivity.class);
                LoginActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.login_et_un:
                if (hasFocus) {
                    rl_un.setActivated(true);
                    rl_pw.setActivated(false);
                }
                break;
            case R.id.login_et_pw:
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
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void showTip(String s) {
        showToast(s);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            //If token is null, all callbacks and messages will be removed.
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}