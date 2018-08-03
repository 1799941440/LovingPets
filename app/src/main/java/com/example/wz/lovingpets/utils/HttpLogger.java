package com.example.wz.lovingpets.utils;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by wz on 2018/7/12.
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger{
    @Override
    public void log(String message) {
        Log.d("HttpLogInfo", message);
    }
}
