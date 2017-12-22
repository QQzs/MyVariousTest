package com.zs.various.view;

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
public class MusicView2 extends View {

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
    private int lineWidth = 6;
    private int  time = 100;
    private boolean flag;

    /**
     * 画布宽高
     */
    private int width, height;

    private int minHeight;

    private int maxHeight;

    private int color;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (flag){
                    if (time < 100){
                        time += 2;
                    }else{
                        flag = false;
                        time -= 2;
                    }
                }else{
                    if (time > 0){
                        time -= 2;
                    }else{
                        flag = true;
                        time += 2;
                    }
                }
                Log.d("My_Log","time = " + time);
                invalidate();
                mHandler.sendEmptyMessageDelayed(1, 15);
            }
        }
    };

    public MusicView2(Context context) {
        this(context,null);
    }

    public MusicView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicView2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("My_Log", "onMeasure: "+ widthSize+"----"+heightSize);
        setMeasuredDimension(widthSize, heightSize);

        width = widthSize- getPaddingLeft() - getPaddingRight();
        height = heightSize - getPaddingTop() - getPaddingBottom();
        minHeight = height / 2;
        maxHeight = height + getPaddingTop();

        //初始化采样点和映射
        samplingX = new float[SAMPLING_SIZE];//因为包括起点和终点所以需要+1个位置
        float gap = width / (float) SAMPLING_SIZE / 2;//确定采样点之间的间距
        float x;
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            x = (2 * i + 1) * gap;
            samplingX[i] = x;
        }
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
//        mHandler.sendEmptyMessage(1);
    }

    public void setAnim(boolean flag) {
        if (mHandler.hasMessages(1)) {
            if (!flag) {
                mHandler.removeMessages(1);
            }
        } else {
            mHandler.sendEmptyMessage(1);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x, y ;
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i] + getPaddingLeft();
            float lineHeight;
            //计算采样点的位置
            if (i%2 == 0){
                lineHeight = (100 - time) / 100f;
            }else{
                lineHeight = time / 100f;
            }
            y = lineHeight * minHeight + getPaddingTop();
            canvas.drawLine(x, maxHeight, x, y, paint1);
        }

    }

}
