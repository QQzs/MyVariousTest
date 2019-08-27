package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class PlayerView extends View {

    private Paint paint;
    /**
     * 采样点的数量
     */
    private static final int SAMPLING_SIZE = 5;
    /**
     * 采样点的X
     */
    private float[] samplingX;
    /**
     * 条形宽度
     */
    private float lineWidth = 8f;
    /**
     * 条形高度
     */
    private float linePercent;

    /**
     * 动画幅度
     */
    private float max = 100f;
    /**
     * 动画时长
     */
    private long duration = 600;
    /**
     * 画布宽高
     */
    private int width, height;

    private int topHeight;

    private int centerHeight;

    private int bottomHeight;
    /**
     * 差值
     */
    private float disparityHeight = 10f;

    private Random mRandom;

    private int color = Color.GRAY;

    private ValueAnimator mAnimator;

    public PlayerView(Context context) {
        this(context,null);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray array = context.obtainStyledAttributes(attrs,
//                R.styleable.MediaView);
//        color = array.getColor(R.styleable.MediaView_color, Color.RED);
//        array.recycle();
        initView();

    }

    private void initView() {
        paint = new Paint();
        paint.setColor(color);
        // 设置宽度
        paint.setStrokeWidth(lineWidth);
        // 设置样式
        paint.setStyle(Paint.Style.FILL);
        // 抗锯齿
        paint.setAntiAlias(true);

        mRandom = new Random();

        initAnimator();
        startAnimator(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerHeight = height / 2;
        //初始化采样点和映射
        samplingX = new float[SAMPLING_SIZE]; // 因为包括起点和终点所以需要+1个位置
        float gap = width / (SAMPLING_SIZE + 1);//确定采样点之间的间距
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            samplingX[i] = gap * (i + 1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (width == 0 || height == 0){
           return;
        }
        float x, y ;
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i];
            //计算采样点的位置
            switch (i){
                case 0:
                    if (linePercent < 0.5f){
                        y = height / 8f + disparityHeight * linePercent / 2.5f;
                    }else{
                        y = height / 8f + disparityHeight * linePercent / 2.2f;
                    }
                    break;
                case 1:
                    if (linePercent < 0.5f){
                        y = height / 4f + disparityHeight * linePercent / 1.5f;
                    }else{
                        y = height / 4f + disparityHeight * linePercent * 1.3f;
                    }
                    break;
                case 2:
                    y = height / 4f + disparityHeight * linePercent * 2f;
                    break;
                case 3:
                    if (linePercent < 0.5f){
                        y = height / 4f + disparityHeight * linePercent * 1.8f;
                    }else{
                        y = height / 4f + disparityHeight * linePercent / 1.4f;
                    }
                    break;
                case 4:
                    if (linePercent < 0.5f){
                        y = height / 8f + disparityHeight * linePercent / 2.6f;
                    }else{
                        y = height / 8f + disparityHeight * linePercent / 1.1f;
                    }
                    break;
                default:
                    y = height / 2f;
                    break;
            }
            // 画圆角矩形
            RectF rf = new RectF();
            rf.left = x;
            rf.top = centerHeight - y;
            rf.right = x + lineWidth;
            rf.bottom = centerHeight + y;
            canvas.drawRoundRect(rf, lineWidth / 2, lineWidth / 2, paint);// 圆角矩形
        }

    }

    /**
     * 动画
     */
    private void initAnimator(){

        mAnimator = ValueAnimator.ofFloat(0,1f,0);
        mAnimator.setDuration(duration);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                linePercent = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 开启关闭动画
     * @param flag
     */
    public void startAnimator(boolean flag){
        if (mAnimator == null){
            initAnimator();
        }
        if (flag){
            if (!mAnimator.isRunning()){
                mAnimator.start();
            }
        }else{
            if (mAnimator.isRunning()){
                mAnimator.end();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        startAnimator(true);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if(mAnimator != null){
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
