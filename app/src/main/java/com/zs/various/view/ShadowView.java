package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;

/**
 * @Author: zs
 * @Date: 2019-07-10 10:29
 * @Description:
 */
public class ShadowView extends AppCompatImageView {

    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    /**
     * 阴影的颜色
     */
    private int mShadowColor;

    /**
     * 阴影 x 轴的偏移量
     */
    private float mShadowDx;

    /**
     * 阴影 y 轴的偏移量
     */
    private float mShadowDy;

    /**
     * 阴影模糊半径
     */
    private float mShadowRadius;

    /**
     * 阴影高度
     */
    private float mShadowHeight;

    /**
     * 阴影的圆角弧度
     */
    private float mShadowViewRadius;

    public ShadowView(Context context) {
        this(context , null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs , R.styleable.ShadowView);
        mShadowColor = typedArray.getColor(R.styleable.ShadowView_shadowColor , Color.GRAY);
        mShadowRadius = typedArray.getDimension(R.styleable.ShadowView_shadowRadius , dp2px(5));
        mShadowDx = typedArray.getDimension(R.styleable.ShadowView_shadowDx , 0);
        mShadowDy = typedArray.getDimension(R.styleable.ShadowView_shadowDy , dp2px(5));
        mShadowHeight = typedArray.getDimension(R.styleable.ShadowView_shadowHeight , dp2px(10));
        mShadowViewRadius = typedArray.getDimension(R.styleable.ShadowView_shadowViewRadius , dp2px(10));

        init();

    }

    private void init(){
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        RectF rect = new RectF(mWidth * 0.1f , 0, mWidth - mWidth * 0.1f, mHeight - mShadowHeight);
        canvas.drawRoundRect(rect, mShadowViewRadius, mShadowViewRadius, mPaint);
    }

    public void setShadowColor(int color){
        mShadowColor = color;
        invalidate();
    }


    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
