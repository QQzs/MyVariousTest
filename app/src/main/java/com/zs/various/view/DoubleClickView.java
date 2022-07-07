package com.zs.various.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.zs.various.util.LogUtil;


/**
 * @Author zhangshuai
 * @ate 2022/5/9
 * @Description
 */
public class DoubleClickView extends LinearLayout {

    /**
     * 按下和收起时间
     */
    private long downTime, upTime;

    private int clickCount;

    public static final int CHECK_CLICK = 1000;
    public static final int ON_CLICK = 2000;

    public static final long DOUBLE_CLICK_TIME = 200;


    private Handler weakHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ON_CLICK) {
                weakHandler.removeMessages(CHECK_CLICK);
                clickCount ++;
                LogUtil.logShow("handleMessage: clickCount = " + clickCount);
                weakHandler.sendEmptyMessageDelayed(CHECK_CLICK, DOUBLE_CLICK_TIME);
            } else if (msg.what == CHECK_CLICK) {
                LogUtil.logShow("CHECK_CLICK handleMessage: clickCount = " + clickCount);
                if (clickCount == 2 && onDoubleClickListener != null) {
                    onDoubleClickListener.onDoubleClick();
                }
                clickCount = 0;
            }
        }
    };


    public DoubleClickView(@NonNull Context context) {
        this(context, null);
    }

    public DoubleClickView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleClickView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.logShow("ACTION_DOWN");
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.logShow("ACTION_UP");
                upTime = System.currentTimeMillis();
                if (upTime - downTime < 500) {
                    weakHandler.sendEmptyMessage(ON_CLICK);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnDoubleClickListener {
        void onDoubleClick();
    }

    private OnDoubleClickListener onDoubleClickListener;

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

}
