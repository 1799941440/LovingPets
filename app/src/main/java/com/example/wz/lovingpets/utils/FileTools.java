package com.example.wz.lovingpets.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.wz.lovingpets.R;

/**
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class FileTools {

    public static void chooseFiles(Activity context, int requestCode) {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*");// 选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intentFromGallery, requestCode);
        context.overridePendingTransition(R.anim.slide2_in, R.anim.slide2_out);//动画
    }
}
