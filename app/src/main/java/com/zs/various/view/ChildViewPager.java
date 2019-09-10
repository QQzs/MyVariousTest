package com.zs.various.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author: zs
 * @Date: 2019-09-07 10:57
 *
 * @Description: 解决 AppBarLayout 和 ViewPager 滑动之间的冲突
 */
public class ChildViewPager extends ViewPager {

    private boolean isScroll = false;
    private float lastX;
    private float lastY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int curX = (int) ev.getRawX();
        int curY = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(curX - lastX);
                dealtY += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (dealtX - dealtY > 5) {
                    isScroll = true;
                } else {
                    isScroll = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                isScroll = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
