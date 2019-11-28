package com.zs.various.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * 通过设置view的setOnTouchListener，
 * 实现点击时变小一下松开时还原的效果
 * @author SamWang
 * @date 2015/12/25	14:39
 */
public abstract class OnClickEffectTouchListener implements View.OnTouchListener {

    /**
     * 点击回调
     */
    public abstract void onTouchClick(View view);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mViewClickEffect.onPressedEffect(v);
                v.setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < v.getWidth() && y > 0 && y < v.getHeight());
                if (v.isPressed() != isInside) {
                    v.setPressed(isInside);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mViewClickEffect.onUnPressedEffect(v);
                v.setPressed(false);
                break;
            case MotionEvent.ACTION_UP:
                mViewClickEffect.onUnPressedEffect(v);
                if (v.isPressed()) {
                    v.performClick();
                    v.setPressed(false);
                }
                onTouchClick(v);
                break;
        }
        return true;
    }

    /**
     * View的点击状态效果定义
     */
    private ViewClickEffect mViewClickEffect = new DefaultClickEffectTranslucence();

    public ViewClickEffect getViewClickEffect() {
        return mViewClickEffect;
    }

    public void setViewClickEffect(ViewClickEffect viewClickEffect) {
        mViewClickEffect = viewClickEffect;
    }
}

