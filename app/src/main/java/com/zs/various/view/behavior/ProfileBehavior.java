package com.zs.various.view.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * @Author: zs
 * @Date: 2019-07-19 10:00
 * @Description:
 */
public class ProfileBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private Context mContext;

    /**
     * view 初始位置 和 结束位置
     */
    private float mStartY;
    private float mEndY;

    /**
     * 跟随view（viewpager）的初始位置
     */
    private float mVPStartY;

    /**
     * ImageView 初始大小 和 结束大小
     */
    private int mStartSize;
    private int mEndSize;

    public ProfileBehavior(Context context) {
        this(context , null);
    }

    public ProfileBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){

        // ImageView 最后停止在 ToolBar 中间
        mEndY = getStatusBarHeight() + dp2px(2.5f);
        mStartSize = dp2px(90);
        mEndSize = dp2px(40);

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof ViewPager;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {

        if (mStartY == 0){
            mStartY = child.getY();
        }

        if (mVPStartY == 0){
            mVPStartY = dependency.getY();
        }

        // 设置位置
        float currentY = mStartY - (mVPStartY - dependency.getY()) / 3;
        if (currentY < mEndY){
            currentY = mEndY;
        }
        child.setY(currentY);

        // 设置大小
        float percent = 1 - (currentY - mEndY) / (mStartY - mEndY);
        int size = (int) (mStartSize - (mStartSize - mEndSize) * percent);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = size;
        lp.height = size;
        child.setLayoutParams(lp);
        return true;
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
