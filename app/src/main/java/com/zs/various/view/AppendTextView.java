package com.zs.various.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zs
 * Date：2017年 11月 27日
 * Time：9:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

@SuppressLint("AppCompatCustomView")
public class AppendTextView extends TextView {

    private String mAppendContent = "ceshi wenben";

    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;

    private Paint mPaint;

    /**
     * 文字绘制所在矩形
     */
    private Rect textRect = new Rect();

    public AppendTextView(Context context) {
        this(context,null);
    }

    public AppendTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AppendTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(40);
        mPaint.setAntiAlias(true);

        //设置调用onDraw方法
//        setWillNotDraw(false);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int height = 200;
//        int width = 200;
//        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        final int heightSpecSize = MeasureSpec.getMode(heightMeasureSpec);
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width,height);
//        }else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width,heightSpecSize);
//        }else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize,height);
//        }
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        width = w;
//        height = h;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);   

//        textRect.left = 0;
//        textRect.top = 0;
//        textRect.right = width;
//        textRect.bottom = height;
//
//        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
//        int baseline = (textRect.top + textRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom)/2;


//        canvas.drawText(mAppendContent,textRect.centerX(),baseline,mPaint);

        this.setText(getText().toString() + mAppendContent);


    }
}
