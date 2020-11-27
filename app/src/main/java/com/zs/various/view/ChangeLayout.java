package com.zs.various.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zs.various.util.LogUtil;

/**
 * @Author: zs
 * @Date: 2020/11/27 7:36 PM
 * @Description:
 */
public class ChangeLayout extends LinearLayout {

    public ChangeLayout(Context context) {
        this(context, null);
    }

    public ChangeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!changed) {
            return;
        }
        //相对于父布局中 l:左  t:上  r:右  b:下
        int offsetY = b;
        if (t == b) {
            offsetY = 0;
        }
        // 高度
        int height = b - t;
        LogUtil.logShow("height = "+ height + " offsetY = " + offsetY);

    }
}
