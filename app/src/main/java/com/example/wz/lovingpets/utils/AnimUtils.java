package com.example.wz.lovingpets.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.JellyInterpolator;

/**
 * 将无关逻辑的动画创建代码放在这里
 */
public class AnimUtils {

    public static AnimatorSet getIconIncomeAnim(Context context, ImageView ic_after,ImageView target){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        //通过测量，获取ivLogo的高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ic_after.measure(w, h);
        int logoHeight = ic_after.getMeasuredHeight();
        float transY = (screenHeight - logoHeight) * 0.3924f;
        AnimatorSet logoAnim = new AnimatorSet();
        ObjectAnimator tranLogo = ObjectAnimator.ofFloat(target, "translationY", 0, -transY);
        ObjectAnimator scaleXLogo = ObjectAnimator.ofFloat(target, "scaleX", 1f, 1f / 3);
        ObjectAnimator scaleYLogo = ObjectAnimator.ofFloat(target, "scaleY", 1f, 1f / 3);
        logoAnim.setDuration(1000);
        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo);
        return logoAnim;
    }

    public static ObjectAnimator getLoginingAnim( ObjectAnimator animator3,View view){
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setRepeatCount(10);
        animator3.setInterpolator(new JellyInterpolator());
        return animator3;
    }

    //输入框动画，传0代表输入框缩小进入登录状态，1代表登录失败复原输入框
    public static AnimatorSet getInputAnim(final View view,float w,int i){

        ValueAnimator animator = null;
        ObjectAnimator animator2 = null;
        AnimatorSet set = new AnimatorSet();
        if(i ==0){
            animator2 = ObjectAnimator.ofFloat(view,
                    "scaleX", 1f, 0.5f);
            animator = ValueAnimator.ofFloat(0, w);
        }else if(i == 1){
            animator2 = ObjectAnimator.ofFloat(view,
                    "scaleX", 0.5f, 1f);
            animator = ValueAnimator.ofFloat(w, 0);
        }
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

        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        return set;
    }
}
