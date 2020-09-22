package com.zs.various.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/9/20 3:38 PM
 * @Description: 多View换行处理
 */
public class BottomMoreLayout extends ViewGroup {

    /**
     * 可见View集合
     */
    private List<View> visibilityView;

    /**
     * 可见View个数
     */
    private int visibilityViewCount;

    /**
     * 单个View宽度
     */
    private int childViewWidth;

    /**
     * 一行最多显示View个数（不能为0）
     */
    private static final int LINE_MAX_COUNT = 5;

    public BottomMoreLayout(Context context) {
        this(context, null);
    }

    public BottomMoreLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        visibilityView = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int count = 0;
        // 筛选可见View
        visibilityView.clear();
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                count += 1;
                visibilityView.add(childView);
            }
        }
        visibilityViewCount = count;
        if (visibilityViewCount == 0) {
            // 防止除数为0
            setMeasuredDimension(widthSize, 0);
            return;
        }
        if (visibilityViewCount < LINE_MAX_COUNT) {
            childViewWidth = widthSize / visibilityViewCount;
        } else {
            childViewWidth = widthSize / LINE_MAX_COUNT;
        }
        int childHeight = 0;
        for (int i = 0, childCount = visibilityView.size(); i < childCount; i++) {
            View child = visibilityView.get(i);
            childHeight = child.getMeasuredHeight();
            // 重新设置view宽度
            int newWidthSpec = MeasureSpec.makeMeasureSpec(childViewWidth, MeasureSpec.EXACTLY);
            measureChild(child, newWidthSpec, heightMeasureSpec);
        }
        setMeasuredDimension(widthSize, (visibilityViewCount / LINE_MAX_COUNT + 1) * childHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 记录 View 左上角位置
        int childLeft = 0;
        int childTop = 0;
        if (visibilityViewCount < LINE_MAX_COUNT) {
            // 单行排列
            for (int i = 0, childCount = visibilityView.size(); i < childCount; i++) {
                View child = visibilityView.get(i);
                int childHeight = child.getMeasuredHeight();
                child.layout(childLeft, childTop, childLeft + childViewWidth, childTop + childHeight);
                childLeft += childViewWidth;
            }
        } else {
            // 多行排列
            for (int i = 0, childCount = visibilityView.size(); i < childCount; i++) {
                View child = visibilityView.get(i);
                int childHeight = child.getMeasuredHeight();
                if (i != 0 && i % LINE_MAX_COUNT == 0) {
                    // 处理换行
                    childLeft = 0;
                    childTop += childHeight;
                }
                child.layout(childLeft, childTop, childLeft + childViewWidth, childTop + childHeight);
                childLeft += childViewWidth;
            }
        }
    }
}
