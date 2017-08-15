package com.zs.various.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zs
 * Date：2017年 07月 27日
 * Time：13:53
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

@SuppressLint("AppCompatCustomView")
public class MyCountDownView extends TextView {

    private long mTime = 100;

    public MyCountDownView(Context context) {
        super(context);
    }

    public MyCountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(long time){
        mTime = time - System.currentTimeMillis();
        if (mTime > 0){
            setText(showTime());
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 1000);
        }else{
            setText("00 : 00 : 00");
        }

    }

    /**
     * 获取时分秒
     * @return
     */
    private String showTime(){
        String hour = String.format("%02d",mTime/(60 * 60 * 1000));
        String minute = String.format("%02d",mTime % (60 * 60 * 1000)/(60 * 1000));
        String second = String.format("%02d",mTime % (60 * 1000)/1000);
        return hour + " : " + minute + " : " + second;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTime = mTime - 1000;
            if (mTime <= 0){
                setText("00 : 00 : 00");
            }else{
                setText(showTime());
                handler.postDelayed(this, 1000);
            }
        }
    };

}
