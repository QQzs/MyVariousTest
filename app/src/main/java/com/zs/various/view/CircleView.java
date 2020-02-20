package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.util.LogUtil;

public class CircleView extends View {

    private Paint paintInner;
    private Paint paintCenter;
    private Paint paintOut;

    private int radiusInner;
    private int radiusCenter;
    private int radiusOut;

    private int width;

    private ValueAnimator mAnimator;

    public CircleView(Context context) {
        super(context);
        initView();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){

        paintInner = new Paint();
        paintInner.setAntiAlias(true);
        paintInner.setColor(0x66ff0000);
        paintInner.setStyle(Paint.Style.FILL);

        paintCenter = new Paint();
        paintCenter.setAntiAlias(true);
        paintCenter.setColor(0x6600ff00);
        paintCenter.setStyle(Paint.Style.FILL);

        paintOut = new Paint();
        paintOut.setAntiAlias(true);
        paintOut.setColor(0x660000ff);
        paintOut.setStyle(Paint.Style.FILL);

        initAnimator();
        LogUtil.Companion.logShow("initView");
    }

    /**
     * 动画
     */
    private void initAnimator(){

        mAnimator = ValueAnimator.ofFloat(0,1);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        mAnimator.setRepeatMode(ValueAnimator.REVERSE);// 循环方式
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                    LogUtil.Companion.logShow("value = " + value);
                if (value >= 0.995){
                    LogUtil.Companion.logShow("value = " + value);
                    radiusInner = width / 4;
                    radiusCenter = width / 2;
                    paintOut.setAlpha(0);
                }else{
                    radiusInner = (int) (radiusInner * (1 - value));
                    radiusCenter = (int) (radiusCenter * (1 - value / 2));
                    paintOut.setAlpha((int) (100 * value));
                }
                invalidate();
            }
        });
        mAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = measureWH(widthMeasureSpec,0) ;
        int heightSize = measureWH(heightMeasureSpec,1);
        setMeasuredDimension(widthSize, heightSize);

    }

    /**
     * 测量宽高
     * type=0 测量宽度， type=1 测量高度
     */
    private int measureWH(int measureSpec, int type){
        int model = MeasureSpec.getMode(measureSpec);//获得当前空间值的一个模式
        int size = MeasureSpec.getSize(measureSpec);//获得当前空间值的推荐值
        int defaultSize = 0; // 默认为0，自己定义
        switch (model){
            case MeasureSpec.EXACTLY: // 当你的控件设置了一个精确的值或者为match_parent时, 为这种模式
                return size;
            case MeasureSpec.AT_MOST: // 当你的控件设置为wrap_content时，为这种模式
                if(type == 0){
                    //测量宽度
                } else {
                    //测量高度
                }
                return defaultSize;
            case MeasureSpec.UNSPECIFIED: // 如果没有指定大小，就设置为默认大小
                return defaultSize;
            default:
                return defaultSize;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w- getPaddingLeft() - getPaddingRight();
        radiusInner = width / 4;
        radiusCenter = width / 2;
        radiusOut = width / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width/2,width/2,radiusOut,paintOut);
        canvas.drawCircle(width/2,width/2,radiusCenter,paintCenter);
        canvas.drawCircle(width/2,width/2,radiusInner,paintInner);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
        mAnimator = null;
    }
}
