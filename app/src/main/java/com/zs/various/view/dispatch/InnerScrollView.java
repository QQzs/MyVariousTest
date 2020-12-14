package com.zs.various.view.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zs
 * Date：2019年 02月 20日
 * Time：9:06
 * —————————————————————————————————————
 * About:内部拦截
 *
 * 推荐：
 * 当子元素占满父元素空间时，使用外部拦截法
 * 当没有占满时使用内部拦截
 * —————————————————————————————————————
 */
public class InnerScrollView extends ScrollView {

    public InnerScrollView(Context context) {
        super(context);
    }

    public InnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // view接收到ACTION_DOWN事件后，告知父容器View接下来的事件序列不要拦截,后面的事件，本View都能接收到
        // 内部拦截法要求父View不能拦截ACTION_DOWN事件
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onInterceptTouchEvent(ev);
//    }

}
