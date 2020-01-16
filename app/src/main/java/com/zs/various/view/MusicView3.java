package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;


/**
 * ValueAnimator 控制动画
 */
public class MusicView3 extends View {

    private Paint paint;
    /**
     * 采样点的数量，越高越精细，
     * 但高于一定限度后人眼察觉不出。
     */
    private static final int SAMPLING_SIZE = 4;
    /**
     * 采样点的X
     */
    private float[] samplingX;
    /**
     * 条形宽度
     */
    private float lineWidth = 4f;
    /**
     * 条形高度
     */
    private float lineHeight;

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

    private int minHeight;

    private int maxHeight;
    /**
     * 差值，有参差不齐的赶脚
     */
    private int disparityHeight = 15;

    private int color;

    private ValueAnimator mAnimator;

    public MusicView3(Context context) {
        this(context,null);
    }

    public MusicView3(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MediaView);
        color = array.getColor(R.styleable.MediaView_color, Color.RED);
        array.recycle();
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
//        mHandler.sendEmptyMessage(MESSAGE_WHAT);
        initAnimator();
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
        height = h - getPaddingTop() - getPaddingBottom();
        minHeight = height / 2;
        maxHeight = height + getPaddingTop() ;
        disparityHeight = maxHeight / 12;

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
            x = samplingX[i] + getPaddingLeft();
            //计算采样点的位置
            switch (i){
                case 0:
                    y = lineHeight / 100f * minHeight + disparityHeight;
                    break;
                case 1:
                    y = (100 - lineHeight) / 100f * minHeight - disparityHeight;
                    break;
                case 2:
                    y = lineHeight / 100f * minHeight;
                    break;
                case 3:
                    y = (100 - lineHeight) / 100f * minHeight + disparityHeight;
                    break;
                default:
                    y = lineHeight / 100f * minHeight;
                    break;
            }
            // 画直线
//            canvas.drawLine(x, maxHeight, x, y + getPaddingTop(), paint);
            // 画圆角矩形
            RectF rf = new RectF();
            rf.left = x - lineWidth / 2;
            rf.top = y + getPaddingTop();
            rf.right = x + lineWidth / 2;
            rf.bottom = maxHeight;
            canvas.drawRoundRect(rf, lineWidth / 2, lineWidth / 2, paint);// 圆角矩形
        }

    }

    /**
     * 动画
     */
    private void initAnimator(){
        mAnimator = ValueAnimator.ofFloat(0,max,0);
        mAnimator.setDuration(duration);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);// 循环方式
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineHeight = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 开启关闭动画
     * @param flag
     */
    public void startAnimator(boolean flag){
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
    protected void onDetachedFromWindow() {
        if(mAnimator != null){
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
