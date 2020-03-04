package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.zs.various.R;


/**
 * @Author: zs
 * @Date: 2019-06-03 13:50
 * @Description: 圆角图片
 */
public class RoundImageView extends AppCompatImageView {
    private Context context;
    private boolean isCircle; // 是否显示为圆形，如果为圆形则设置的corner无效
    private int borderWidth; // 边框宽度
    private int borderColor = Color.TRANSPARENT; // 边框颜色
    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int maskColor; // 遮罩颜色

    private int width;
    private int height;
    private float radius;
    private RectF srcRectF; // 图片占的矩形区域
    private Paint paint;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.RoundImageView_is_circle) {
                isCircle = ta.getBoolean(attr, isCircle);
            } else if (attr == R.styleable.RoundImageView_border_width) {
                borderWidth = ta.getDimensionPixelSize(attr, borderWidth);
            } else if (attr == R.styleable.RoundImageView_border_color) {
                borderColor = ta.getColor(attr, borderColor);
            } else if (attr == R.styleable.RoundImageView_corner_radius) {
                cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);
            } else if (attr == R.styleable.RoundImageView_mask_color) {
                maskColor = ta.getColor(attr, maskColor);
            }
        }
        ta.recycle();
        srcRectF = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initSrcRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawMask(canvas);
        setRound();
    }

    private void setRound(){
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (isCircle){
                    outline.setOval(0, 0, width, height);
                }else{
                    outline.setRoundRect(0, 0, width, height, cornerRadius);
                }
            }
        });
        setClipToOutline(true);
    }

    /**
     * 设置边框
     */
    private void drawBorder(Canvas canvas) {
        initBorderPaint();
        if (isCircle) {
            canvas.drawCircle(width / 2.0f , height / 2.0f , radius , paint);
        } else {
            canvas.drawRoundRect(srcRectF , cornerRadius , cornerRadius , paint);
        }
    }

    /**
     * 设置遮罩
     */
    private void drawMask(Canvas canvas){
        if (maskColor != 0){
            initMaskPaint();
            if (isCircle) {
                canvas.drawCircle(width / 2.0f , height / 2.0f , radius , paint);
            } else {
                canvas.drawRoundRect(srcRectF , cornerRadius , cornerRadius , paint);
            }
        }
    }

    private void initBorderPaint() {
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void initMaskPaint(){
        paint.setColor(maskColor);
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        if (isCircle) {
            radius = Math.min(width, height) / 2.0f;
            srcRectF.set(width / 2.0f - radius, height / 2.0f - radius, width / 2.0f + radius, height / 2.0f + radius);
        } else {
            srcRectF.set(0, 0, width, height);
        }
    }

    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        initSrcRectF();
        invalidate();
    }

    public void setBorderWidth(int width) {
        borderWidth = width;
        invalidate();
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dp2px(context, cornerRadius);
        invalidate();
    }

    public void setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

