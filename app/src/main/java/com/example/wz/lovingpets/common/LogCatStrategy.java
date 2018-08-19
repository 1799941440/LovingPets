package com.example.wz.lovingpets.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.LogStrategy;

/**
 * 由于Logger在AndroidStudio3.1有显示不对齐的Bug，需要重写一下
 */
public class LogCatStrategy implements LogStrategy {

    private Handler handler ;
    private long lastTime = SystemClock.uptimeMillis();
    private long offset = 10;

    public LogCatStrategy() {
        HandlerThread thread = new HandlerThread("thread_print");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    @Override
    public void log(final int priority, final String tag, @NonNull final String message) {

        lastTime += offset;
        if (lastTime < SystemClock.uptimeMillis()) {
            lastTime = SystemClock.uptimeMillis() + offset;
        }
        final long tmp = lastTime;
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                Log.println(priority, tag, message);
            }
        }, tmp);

    }
}