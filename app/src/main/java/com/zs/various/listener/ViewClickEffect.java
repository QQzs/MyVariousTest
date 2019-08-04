package com.zs.various.listener;

import android.view.View;

/**
 * @Author: zs
 * @Date: 2019-05-21 14:05
 * @Description:
 */
public interface ViewClickEffect {

    /**
     * 按下去的效果
     * @param view
     */
    void onPressedEffect(View view);

    /**
     * 释放的效果
     * @param view
     */
    void onUnPressedEffect(View view);

}
