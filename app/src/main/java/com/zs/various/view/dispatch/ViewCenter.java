package com.zs.various.view.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by zs
 * Date：2018年 10月 24日
 * Time：16:17
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ViewCenter extends RelativeLayout {

    public ViewCenter(Context context) {
        super(context);
    }

    public ViewCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN){
//            Log.d("My_Log"," center ====  onInterceptTouchEvent ACTION_DOWN ");
////            return true;
//        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
//            Log.d("My_Log"," center ====  onInterceptTouchEvent ACTION_MOVE ");
////            return true;
//        }else if (ev.getAction() == MotionEvent.ACTION_UP){
//            Log.d("My_Log"," center ====  onInterceptTouchEvent ACTION_UP ");
////            return true;
//        }else{
////            return true;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("My_Log"," center ====  dispatchTouchEvent ACTION_DOWN ");
//            return true;
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("My_Log"," center ====  dispatchTouchEvent ACTION_MOVE ");
//            return true;
        }else if (ev.getAction() == MotionEvent.ACTION_UP){
            Log.d("My_Log"," center ====  dispatchTouchEvent ACTION_UP ");
//            return true;
        }
//        return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("My_Log"," center ====  onTouchEvent ACTION_DOWN ");
//            return true;
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("My_Log"," center ====  onTouchEvent ACTION_MOVE ");
//            return true;
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            Log.d("My_Log"," center ====  onTouchEvent ACTION_UP ");
//            return true;
        }
//        return true;
        return super.onTouchEvent(event);
    }

}
