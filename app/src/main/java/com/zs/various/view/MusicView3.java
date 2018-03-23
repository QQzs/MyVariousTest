package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zs.various.R;


/**
 * Created by zhangshuai on 16/8/17.
 */
public class MusicView3 extends View {

    private Paint paint1;

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
    private int lineWidth = 4;
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

    private int MESSAGE_WHAT = 2222;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_WHAT) {
                initAnimator();
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT,duration);
            }
            super.handleMessage(msg);
        }
    };

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = measureWH(widthMeasureSpec,0) ;
        int heightSize = measureWH(heightMeasureSpec,1);
        setMeasuredDimension(widthSize, heightSize);
        Log.d("My_Log", "onMeasure: "+ widthSize+"----"+heightSize);

        width = widthSize- getPaddingLeft() - getPaddingRight();
        height = heightSize - getPaddingTop() - getPaddingBottom();
        minHeight = height / 2;
        maxHeight = height + getPaddingTop();
        disparityHeight = maxHeight / 12;

        //初始化采样点和映射
        samplingX = new float[SAMPLING_SIZE];//因为包括起点和终点所以需要+1个位置
        float gap = width / (float) SAMPLING_SIZE / 2;//确定采样点之间的间距
        float x;
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            x = (2 * i + 1) * gap;
            samplingX[i] = x;
        }
    }

    /**
     * 测量宽高
     * type=0 测量宽度， type=1 测量高度
     */
    private int measureWH(int measureSpec, int type){
        int model = MeasureSpec.getMode(measureSpec);//获得当前空间值的一个模式
        int size = MeasureSpec.getSize(measureSpec);//获得当前空间值的推荐值
        switch (model){
            case MeasureSpec.EXACTLY: // 当你的控件设置了一个精确的值或者为match_parent时, 为这种模式
                return size;
            case MeasureSpec.AT_MOST: // 当你的控件设置为wrap_content时，为这种模式
                if(type == 0){
                    //测量宽度
//                    size = (int) paint.measureText(labels[0]);
//                    return size;
                } else {
                    //测量高度
//                    return size;
                }
            case MeasureSpec.UNSPECIFIED: //尽可能的多
                break;
        }
        return 0;
    }

    private void initView() {
        paint1 = new Paint();
        paint1.setColor(color);
        // 设置宽度
        paint1.setStrokeWidth(lineWidth);
        // 设置样式
        paint1.setStyle(Paint.Style.STROKE);
        // 抗锯齿
        paint1.setAntiAlias(true);
        mHandler.sendEmptyMessage(MESSAGE_WHAT);
    }

    public void startAnimator(boolean flag){

        if (mHandler.hasMessages(MESSAGE_WHAT)) {
            if (!flag) {
                mHandler.removeMessages(MESSAGE_WHAT);
            }
        } else {
            mHandler.sendEmptyMessage(MESSAGE_WHAT);
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
//            if (i%2 == 0){
//                y = lineHeight / 100f * minHeight + getPaddingTop();
//            }else{
//                y = (100 - lineHeight) / 100f * minHeight + getPaddingTop();
//            }

            switch (i){
                case 0:
                    y = lineHeight / 100f * minHeight + getPaddingTop() + disparityHeight;
                    break;
                case 1:
                    y = (100 - lineHeight) / 100f * minHeight + getPaddingTop() - disparityHeight;
                    break;
                case 2:
                    y = lineHeight / 100f * minHeight + getPaddingTop();
                    break;
                case 3:
                    y = (100 - lineHeight) / 100f * minHeight + getPaddingTop() + disparityHeight;
                    break;
                default:
                    y = lineHeight / 100f * minHeight + getPaddingTop();
                    break;
            }
            canvas.drawLine(x, maxHeight, x, y, paint1);
        }

    }

    /**
     * 动画
     */
    private void initAnimator(){

        ValueAnimator animator = ValueAnimator.ofFloat(0,max,0);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineHeight = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}