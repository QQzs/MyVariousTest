package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;
import com.zs.various.util.LogUtil;


/**
 *
 */
public class CustomView extends View {

    /**
     * 条形的画笔
     */
    private Paint paint;

    /**
     * 圆环的画笔
     */
    private Paint paintCircle;


    private int width, height;

    private int minHeight;

    private int maxHeight;

    private int color;

    private ValueAnimator mAnimator;

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
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
//        setMeasuredDimension(widthSize, heightSize);
//        Log.d("My_Log", "onMeasure: "+ widthSize+"----"+heightSize);

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
                LogUtil.Companion.logShow("type = " + type + " MeasureSpec.EXACTLY");
                return size;
            case MeasureSpec.AT_MOST: // 当你的控件设置为wrap_content时，为这种模式
                LogUtil.Companion.logShow("type = " + type + " MeasureSpec.AT_MOST");
                if(type == 0){
                    //测量宽度
                } else {
                    //测量高度
                }
                return defaultSize;
            case MeasureSpec.UNSPECIFIED: // 如果没有指定大小，就设置为默认大小
                LogUtil.Companion.logShow("type = " + type + " MeasureSpec.UNSPECIFIED");
                return defaultSize;
            default:
                return defaultSize;
        }
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(color);
        // 设置宽度
        paint.setStrokeWidth(2);
        // 设置样式
        paint.setStyle(Paint.Style.FILL);
        // 抗锯齿
        paint.setAntiAlias(true);

        paintCircle = new Paint();
        paintCircle.setColor(color);
        // 设置宽度
        paintCircle.setStrokeWidth(2);
        // 设置样式
        paintCircle.setStyle(Paint.Style.STROKE);
        // 抗锯齿
        paintCircle.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
