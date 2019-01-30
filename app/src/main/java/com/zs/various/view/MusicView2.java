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
 *
 * Handler控制动画
 *
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
        int widthSize = measureWH(widthMeasureSpec,0) ;
        int heightSize = measureWH(heightMeasureSpec,1);
        setMeasuredDimension(widthSize, heightSize);

        // onMeasure会走多遍，看view在布局中包裹几层
        if (width == 0){
            width = widthSize- getPaddingLeft() - getPaddingRight();
            height = heightSize - getPaddingTop() - getPaddingBottom();
            minHeight = height / 2;
            maxHeight = height + getPaddingTop();

            //初始化采样点和映射
            samplingX = new float[SAMPLING_SIZE]; // 因为包括起点和终点所以需要+1个位置
            float gap = width / (SAMPLING_SIZE + 1);//确定采样点之间的间距
            for (int i = 0; i < SAMPLING_SIZE; i++) {
                samplingX[i] = gap * (i + 1);
            }
        }

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
