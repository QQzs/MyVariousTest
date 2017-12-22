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


/**
 * Created by zhangshuai on 16/8/17.
 */
public class MediaView extends View {

    private Paint paint;

    /**
     * 采样点的数量，越高越精细，
     * 但高于一定限度后人眼察觉不出。
     */
    private static final int SAMPLING_SIZE = 40;
    /**
     * 采样点的X
     */
    private float[] samplingX;

    /**
     * 画布宽高
     */
    private int width, height;
    /**
     * 画布中心的高度
     */
    private int centerHeight;

    private int loc;

    private int maxHeight;

    private int color;

    private int location;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                invalidate();
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }
    };

    public MediaView(Context context) {
        super(context);
        initView();
    }

    public MediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MediaView);
        color = array.getColor(R.styleable.MediaView_color, Color.RED);
        location = array.getInt(R.styleable.MediaView_loca,0);
        array.recycle();
        initView();
    }

    public MediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView(){

        paint = new Paint();
        paint.setColor(color);
        // 设置宽度
        paint.setStrokeWidth(5);
        // 设置样式
        paint.setStyle(Paint.Style.STROKE);
        // 抗锯齿
        paint.setAntiAlias(true);
//        mHandler.sendEmptyMessage(1);


    }

    public void setAnim(boolean flag){
        if (mHandler.hasMessages(1)){
            if (!flag){
               mHandler.removeMessages(1);
            }
        }else{
            mHandler.sendEmptyMessage(1);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();
        centerHeight = height/2;

        if (location == 0){
            loc = centerHeight;
            maxHeight = centerHeight;
        }else{
            loc = height;
            maxHeight = height;
        }

        //初始化采样点和映射
        samplingX = new float[SAMPLING_SIZE + 1];//因为包括起点和终点所以需要+1个位置
        //绘制背景
//        canvas.drawColor(Color.WHITE);

        float gap = width / (float) SAMPLING_SIZE;//确定采样点之间的间距
        float x,y;
        for (int i = 0; i <= SAMPLING_SIZE; i++) {
            x = i * gap;
            samplingX[i] = x;
        }

        for (int i = 0; i <= SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i];
            //计算采样点的位置
            y = (float) (Math.random() * (maxHeight - 20) + 20);
            canvas.drawLine(x,loc,x,loc - y,paint);
        }


    }
}
