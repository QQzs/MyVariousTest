package com.zs.various.view;

/**
 * Created by zs
 * Date：2017年 11月 21日
 * Time：10:15
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.zs.various.R;
import com.zs.various.util.DrawableUtil;


/**
 * 带边框属性的RelativeLayout
 *
 * @author zs
 * @date 2017/21/11
 */
public class BorderRelativeLayout extends RelativeLayout {

    private int strokeWidth;    // 边框线宽
    private int strokeColor;    // 边框颜色
    private int contentColor;   // 背景颜色
    private int pressedColor;   // 按下背景颜色
    private int cornerRadius;   // 圆角半径

    private Paint mPaint = new Paint();                 // 画边框所使用画笔对象
    private RectF mRectF = new RectF();                 // 画边框要使用的矩形

    public BorderRelativeLayout(Context context) {
        this(context, null);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.BorderTextView_strokeWidth, 0);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.BorderTextView_cornerRadius, 0);
        strokeColor = ta.getColor(R.styleable.BorderTextView_strokeColor, Color.TRANSPARENT);
        contentColor = ta.getColor(R.styleable.BorderTextView_contentBackColor, Color.TRANSPARENT);
        pressedColor = ta.getColor(R.styleable.BorderTextView_contentPressedColor, contentColor);
        ta.recycle();
        initView();
        //设置调用onDraw方法
        setWillNotDraw(false);
    }

    public void initView(){
        // 初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);     // 空心效果
        mPaint.setAntiAlias(true);               // 设置画笔为无锯齿
        mPaint.setStrokeWidth(strokeWidth);      // 线宽
        // 设置背景
        setBackground(DrawableUtil.getPressedSelector(contentColor , pressedColor , cornerRadius));

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // 设置画笔颜色
        mPaint.setColor(strokeColor);
        // 画空心圆角矩形
        mRectF.left = mRectF.top = 0.5f * strokeWidth;
        mRectF.right = getMeasuredWidth() - 0.5f * strokeWidth;
        mRectF.bottom = getMeasuredHeight() - 0.5f * strokeWidth;
        canvas.drawRoundRect(mRectF, cornerRadius, cornerRadius, mPaint);
        super.onDraw(canvas);
    }

    /**
     * 修改边框宽度
     * @param roederWidth  传值：px
     */
    public void setStrokeWidth(int roederWidth){
        try {
            strokeWidth = roederWidth;
            invalidate();
        }catch (Exception e){
            Log.e("My_Error",e.toString());
        }

    }

    /**
     * 修改边框颜色
     * @param colorResource  传值：R.color.XXXX
     */
    public void setStrokeColor(int colorResource){
        try {
            strokeColor = ContextCompat.getColor(getContext(), colorResource);
            invalidate();
        }catch (Exception e){
            Log.e("My_Error",e.toString());
        }

    }

    /**
     * 修改背景颜色
     * @param colorResource  传值：R.color.XXXX
     */
    public void setContentColorResource(int colorResource){
        try {
            contentColor = ContextCompat.getColor(getContext(), colorResource);
            setBackground(DrawableUtil.getPressedSelector(contentColor , contentColor , cornerRadius));
        }catch (Exception e){
            Log.e("My_Error",e.toString());
        }

    }

}