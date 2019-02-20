package com.zs.various.view.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zs
 * Date：2018年 10月 24日
 * Time：16:17
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ViewTop extends View {

    public ViewTop(Context context) {
        super(context);
    }

    public ViewTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("My_Log"," top ====  dispatchTouchEvent ACTION_DOWN ");
//            return true;
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("My_Log"," top ====  dispatchTouchEvent ACTION_MOVE ");
//            return true;
        }else if (ev.getAction() == MotionEvent.ACTION_UP){
            Log.d("My_Log"," top ====  dispatchTouchEvent ACTION_UP ");
//            return true;
        }
//        return false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("My_Log"," top ====  onTouchEvent ACTION_DOWN ");
//            return true;
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("My_Log"," top ====  onTouchEvent ACTION_MOVE ");
//            return true;
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            Log.d("My_Log"," top ====  onTouchEvent ACTION_UP ");
//            return false;
        }
//        return false;
        return super.onTouchEvent(event);
    }
}
