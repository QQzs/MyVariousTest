package com.zs.various.view;

/**
 * Created by zs
 * Date：2017年 11月 21日
 * Time：10:15
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import com.zs.various.R;
import com.zs.various.util.DensityUtil;


/**
 * 带边框属性的TextView
 *
 * @author zs
 * @date 2017/21/11
 */
@SuppressLint("AppCompatCustomView")
public class BorderTextView extends TextView {

    public static final float DEFAULT_STROKE_WIDTH = 1.0f;    // 默认边框宽度, 1dp
    public static final float DEFAULT_CORNER_RADIUS = 2.0f;   // 默认圆角半径, 2dp
    public static final float DEFAULT_LR_PADDING = 6f;      // 默认左右内边距
    public static final float DEFAULT_TB_PADDING = 2f;      // 默认上下内边距

    private int strokeWidth;    // 边框线宽
    private int strokeColor;    // 边框颜色
    private int contentColor;   // 背景颜色
    private int cornerRadius;   // 圆角半径
    private boolean mFollowTextColor; // 边框颜色是否跟随文字颜色

    private Paint mPaint = new Paint();     // 画边框所使用画笔对象
    private Paint mPaintBackground = new Paint();     // 画边框所使用画笔对象

    private RectF mRectF;                   // 画边框要使用的矩形


    /*起始点*/
    private int mInitX;
    private int mInitY;

    /*绘制的半径*/
    private int mRadius;
    private int mStep;
    private int mDrawRadius;

    private boolean mDrawFinish;

    private boolean mDrawBack  ;

    private final int DURATION = 700;
    private final int FREQUENCY = 10;
    private int mCycle;
    private final Rect mRect = new Rect();

    private Paint mRevealPaint = new Paint(Paint.ANTI_ALIAS_FLAG);





    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 将DIP单位默认值转为PX
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_STROKE_WIDTH, displayMetrics);
        cornerRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_RADIUS, displayMetrics);

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.BorderTextView_strokeWidth, strokeWidth);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.BorderTextView_cornerRadius, cornerRadius);
        strokeColor = ta.getColor(R.styleable.BorderTextView_strokeColor, Color.TRANSPARENT);
        contentColor = ta.getColor(R.styleable.BorderTextView_contentBackColor, Color.TRANSPARENT);
        mFollowTextColor = ta.getBoolean(R.styleable.BorderTextView_followTextColor, true);
        ta.recycle();

        // 如果使用时没有设置内边距, 设置默认边距
//        int paddingLeft = getPaddingLeft() == 0 ? (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LR_PADDING, displayMetrics) : getPaddingLeft();
//        int paddingRight = getPaddingRight() == 0 ? (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LR_PADDING,
//                displayMetrics) : getPaddingRight();
//        int paddingTop = getPaddingTop() == 0 ? (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TB_PADDING, displayMetrics) : getPaddingTop();
//        int paddingBottom = getPaddingBottom() == 0 ? (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TB_PADDING,
//                displayMetrics) : getPaddingBottom();
//        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        initView(context);
    }

    private void initView(Context context){

        mRectF = new RectF();
        // 边框默认颜色与文字颜色一致
//        if (strokeColor == Color.TRANSPARENT)
//            strokeColor = getCurrentTextColor();

        mPaint.setStyle(Paint.Style.STROKE);     // 空心效果
        mPaint.setAntiAlias(true);               // 设置画笔为无锯齿
        mPaint.setStrokeWidth(strokeWidth);      // 线宽

        mPaintBackground.setStyle(Paint.Style.FILL);
        mPaintBackground.setAntiAlias(true);               // 设置画笔为无锯齿

        // 设置边框线的颜色, 如果声明为边框跟随文字颜色且当前边框颜色与文字颜色不同时重新设置边框颜色
        if (mFollowTextColor && strokeColor != getCurrentTextColor())
            strokeColor = getCurrentTextColor();
        mPaint.setColor(strokeColor);


        mRevealPaint.setColor(0x10000000);
        mCycle = DURATION / FREQUENCY;
        mDrawFinish = true;


        int color1 = ContextCompat.getColor(context,R.color.color_2);
        int color2 = ContextCompat.getColor(context,R.color.color_0);
        setBackground(generatePressedSelector(createShape(color1),createShape(color2)));

    }

    /**
     * 用java代码的方式动态生成状态选择器
     */
    public static Drawable generatePressedSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//  状态  , 设置按下的图片
        drawable.addState(new int[]{}, normal);//默认状态,默认状态下的图片
        //根据SDK版本设置状态选择器过度动画/渐变选择器/渐变动画
        if (Build.VERSION.SDK_INT > 10) {
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);
        }
        return drawable;
    }

    public static GradientDrawable createShape(int color){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setCornerRadius(DensityUtil.dip2px(10));//设置4个角的弧度
        drawable.setColor(color);// 设置颜色
        return drawable;


    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        // 画空心圆角矩形
        mRectF.left = mRectF.top = 0.5f * strokeWidth;
        mRectF.right = getMeasuredWidth() - strokeWidth;
        mRectF.bottom = getMeasuredHeight() - strokeWidth;
        canvas.drawRoundRect(mRectF, cornerRadius, cornerRadius, mPaint);

        // 画背景颜色
        mPaintBackground.setColor(contentColor);
        canvas.drawRoundRect(mRectF, cornerRadius, cornerRadius, mPaintBackground);

        // ==============================================

        if (mDrawFinish) {
            super.onDraw(canvas);
            return;
        }
        canvas.drawColor(0x08000000);
        super.onDraw(canvas);
        if (mStep == 0) {
            return;
        }
        mDrawRadius = mDrawRadius + mStep;

        if (mDrawRadius > mRadius) {
            mDrawRadius = 0;
            canvas.drawCircle(mInitX, mInitY, mRadius, mRevealPaint);
            mDrawFinish = true;
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }

        canvas.drawCircle(mInitX, mInitY, mDrawRadius, mRevealPaint);
        ViewCompat.postInvalidateOnAnimation(this);

        // ==============================================

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    private void updateDrawData() {
        int radiusLeftTop = (int) Math.sqrt((mRect.left - mInitX) * (mRect.left - mInitX) +
                (mRect.top - mInitY) * (mRect.top - mInitY));
        int radiusRightTop = (int) Math.sqrt((mRect.right - mInitX) * (mRect.right - mInitX) +
                (mRect.top - mInitY) * (mRect.top - mInitY));
        int radiusLeftBottom = (int) Math.sqrt((mRect.left - mInitX) * (mRect.left - mInitX) +
                (mRect.bottom - mInitY) * (mRect.bottom - mInitY));
        int radiusRightBottom = (int) Math.sqrt((mRect.right - mInitX) * (mRect.right - mInitX) +
                (mRect.bottom - mInitY) * (mRect.bottom - mInitY));
        mRadius = getMax(radiusLeftTop, radiusRightTop, radiusLeftBottom, radiusRightBottom);
        mStep = mRadius/mCycle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mDrawFinish = false;
                int index = MotionEventCompat.getActionIndex(event);
                int eventId = MotionEventCompat.getPointerId(event, index);
                if (eventId != -1) {
                    mInitX = (int) MotionEventCompat.getX(event, index);
                    mInitY = (int) MotionEventCompat.getY(event, index);
                    updateDrawData();
                    ViewCompat.postInvalidateOnAnimation(this);
                }
//                return true;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mStep = (int) (2.5f * mStep);
                mDrawBack = true ;
                break;
        }
        return super.onTouchEvent(event);
    }

    private int getMax(int... radius) {
        if (radius.length == 0) {
            return 0;
        }
        int max = radius[0];
        for (int m : radius) {
            if (m > max) {
                max = m;
            }
        }
        return max;
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
            invalidate();
        }catch (Exception e){
            Log.e("My_Error",e.toString());
        }

    }
}