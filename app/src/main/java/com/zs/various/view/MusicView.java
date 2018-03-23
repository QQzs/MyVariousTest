package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;

import java.util.Random;


/**
 * Created by zhangshuai on 16/8/17.
 */
public class MusicView extends View {

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
//            super.handleMessage(msg);
            if (msg.what == 1) {
                invalidate();
                mHandler.sendEmptyMessageDelayed(1, 350);
            }
        }
    };

    public MusicView(Context context) {
        super(context);
        initView();
    }

    public MusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MediaView);
        color = array.getColor(R.styleable.MediaView_color, Color.RED);
        array.recycle();
        initView();
    }

    public MusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
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

        float x, y;
        Random random = new Random();
        for (int i = 0; i < SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i] + getPaddingLeft();
            //计算采样点的位置
            y = random.nextFloat() * minHeight + getPaddingTop();
            canvas.drawLine(x,maxHeight,x,y,paint1);
        }

    }

}