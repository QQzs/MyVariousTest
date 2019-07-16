package com.zs.various.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author: zs
 * @Date: 2019-07-10 10:29
 * @Description:
 */
public class ShadowView extends AppCompatImageView {

    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    private int mColor = Color.BLACK;

    private float mShadowAlpha;
    private float mShadowX;
    private float mShadowY;
    private float mShadowPadding;
    private float mShadowRadius;

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mShadowAlpha = dp2px(6);
        mShadowX = dp2px(0);
        mShadowY = dp2px(6);

        mShadowPadding = dp2px(15);
        mShadowRadius = dp2px(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(mShadowAlpha, mShadowX, mShadowY, mColor);
        RectF rect = new RectF(mWidth * 0.1f , 0, mWidth - mWidth * 0.1f, mHeight - mShadowPadding);
        canvas.drawRoundRect(rect, mShadowRadius, mShadowRadius, mPaint);
    }

    public void setBack(int color){
        mColor = color;
        invalidate();
    }


    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
