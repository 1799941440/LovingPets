package com.example.wz.lovingpets.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private int lastY = 0;
    private static final int SCROLL_TIME = 20;
    private static final int SCROLL_WHAT = 111;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_WHAT:
                    int scrollY = getScrollY();
                    if (lastY != scrollY) {
                        lastY = scrollY;
                        scrollYListener.onScrollChanged(scrollY);
                        handler.sendEmptyMessageDelayed(SCROLL_WHAT, SCROLL_TIME);
                    }
                    break;
            }
        }
    };

    private ScrollListener scrollListener = null;
    public interface ScrollListener {
        void onScrollChanged(int x, int y, int oldx, int oldy,int computeVerticalScrollRange);
    }

    private ScrollYListener scrollYListener;
    public interface ScrollYListener {
        void onScrollChanged(int y);
    }

    public MyScrollView(Context context) {
        super(context);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollYListener != null) {
            scrollYListener.onScrollChanged(t);
        }
        if(scrollListener != null){
            scrollListener.onScrollChanged(l,t,oldl,oldt,computeVerticalScrollRange());
        }
    }

    public void setScrollViewListener(ScrollListener scrollViewListener) {
        this.scrollListener = scrollViewListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (scrollYListener != null) {
                    handler.sendEmptyMessage(SCROLL_WHAT);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
