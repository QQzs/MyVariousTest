package com.zs.various.view.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by zs
 * Date：2019年 02月 20日
 * Time：11:26
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ViewBack extends RelativeLayout{

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;

    public ViewBack(Context context) {
        super(context);
        initView();
    }

    public ViewBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ViewBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("My_Log", "back:  dispatchTouchEvent");
//        return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                Log.d("My_Log", "Touch: ACTION_DOWN");
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("My_Log", "Touch: ACTION_MOVE");
                int dx =(int)event.getRawX() - lastX;
                int dy =(int)event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if(left < 0){
                    left = 0;
                    right = left + getWidth();
                }
                if(right > screenWidth){
                    right = screenWidth;
                    left = right - getWidth();
                }
                if(top < 0){
                    top = 0;
                    bottom = top + getHeight();
                }
                if(bottom > screenHeight){
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }
                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Log.d("My_Log", "Touch: ACTION_UP");
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
