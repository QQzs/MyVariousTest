package com.zs.various.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;

public class MarqueeTextView extends View {

    private String mContent;
    /**
     * 进度动画
     */
    private ValueAnimator mAnimator;
    private ValueAnimator mAnimator2;
    private Paint mPaint;
    /**
     * 左边间距
     */
    private int mOffset;
    private int mOffset2;
    private int mBaseLine;
    private Rect mTargetRect;
    private Rect mTargetRect2;
    /**
     * 内容的宽度
     */
    private int mTextWidth;
    /**
     * view的宽度
     */
    private int mViewWidth;
    private int mAnimSpeed;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        int textColor = typedArray.getColor(R.styleable.MarqueeTextView_marqueeTextColor, 0);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.MarqueeTextView_marqueeTextSize, 0);
        mAnimSpeed = typedArray.getInteger(R.styleable.MarqueeTextView_marqueeSpeed , 2);
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mContent == null) {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }

        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int height = fontMetrics.bottom - fontMetrics.top + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        int top = getPaddingTop();
        int bottom = top + fontMetrics.bottom - fontMetrics.top;
        int left = getPaddingLeft() + mOffset;
        int right = left + mTextWidth;
        if (mTargetRect == null) {
            mTargetRect = new Rect();
        }
        if (mTargetRect2 == null) {
            mTargetRect2 = new Rect();
        }
        mTargetRect.set(left, top, right, bottom);
        mTargetRect2.set(left, top, right, bottom);
        mBaseLine = (mTargetRect.bottom + mTargetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mContent == null || mTargetRect == null || mTargetRect2 == null) {
            return;
        }
        mTargetRect.left = getPaddingLeft() + mOffset;
        mTargetRect.right = mTargetRect.left + mTextWidth;
        canvas.drawText(mContent, mTargetRect.left, mBaseLine, mPaint);

        if (mTextWidth < mViewWidth) {
            mTargetRect2.right = mViewWidth + mOffset2;
            mTargetRect2.left = mTargetRect2.right - mTextWidth - getPaddingRight();
        } else {
            mTargetRect2.right = mTextWidth + mViewWidth/3 + mOffset2;
            mTargetRect2.left = mTargetRect2.right - mTextWidth - getPaddingRight();
        }
        canvas.drawText(mContent, mTargetRect2.right, mBaseLine, mPaint);
    }

    public void setText(String text) {
        mContent = text;
        mTextWidth = (int) (mPaint.measureText(mContent));
        if(null == mAnimator){
            mAnimator = ValueAnimator.ofFloat(0, mTextWidth);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mOffset -= mAnimSpeed;
                    if (mTextWidth < getWidth()) {
                        if (mOffset < -getWidth()) {
                            mOffset = getWidth();//定位到最右边
                        }
                    } else {
                        if (mOffset < -(getWidth()/3 + mTextWidth)) {
                            mOffset = mTextWidth + getWidth()/3;//保证与第二个动画同步
                        }
                    }
                    invalidate();
                }
            });
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setRepeatMode(ValueAnimator.REVERSE);
            //5.为ValueAnimator设置目标对象并开始执行动画
            mAnimator.setTarget(this);

        }
        if(null == mAnimator2){
            mAnimator2 = ValueAnimator.ofFloat(getWidth(), 0);
            mAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mOffset2 -= mAnimSpeed;
                    if (mTextWidth < getWidth()) {
                        if (mOffset2 < -2*getWidth()) {
                            mOffset2 = 0;//定位到最右边
                        }
                    } else {
                        if (mOffset2 < -2*(getWidth()/3 + mTextWidth)) {
                            mOffset2 = 0;
                        }
                    }
                    invalidate();
                }
            });
            mAnimator2.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator2.setRepeatMode(ValueAnimator.REVERSE);
            //5.为ValueAnimator设置目标对象并开始执行动画
            mAnimator2.setTarget(this);
        }
    }

    public void startAnim(){
        if(null != mAnimator){
            mAnimator.start();
        }
        if(null != mAnimator2){
            mAnimator2.start();
        }
    }

    public void stopAnim(){
        if(null != mAnimator && mTextWidth > getMeasuredWidth()){
            mAnimator.cancel();
        }
        if(null != mAnimator2 && mTextWidth > getMeasuredWidth()){
            mAnimator2.cancel();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == View.VISIBLE){
            startAnim();
        }else{
            stopAnim();
        }
    }

}
