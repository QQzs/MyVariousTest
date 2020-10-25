package com.zs.various.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zs.various.R;
import com.zs.various.util.LogUtil;


public class LongTextViewOld extends View {
    /**
     * 滚动内容
     */
    private String mContent;
    /**
     * 进度动画
     */
    private ValueAnimator mAnimator;
    private Paint mPaint;
    /**
     * 左边间距
     */
    private int mOffset;
    private int mBaseLine;
    private Rect mTargetRect;
    /**
     * 内容的宽度
     */
    private int mTextWidth;

    private int height;
    private boolean mLongTag;

    private LongTextListener mListener;

    private int mAnimSpeed;
    private Paint.FontMetricsInt fontMetrics;

    public LongTextViewOld(Context context) {
        this(context, null);
    }

    public LongTextViewOld(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongTextViewOld(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        int textColor = typedArray.getColor(R.styleable.MarqueeTextView_marqueeTextColor, 0);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.MarqueeTextView_marqueeTextSize, 0);
        mAnimSpeed = typedArray.getInteger(R.styleable.MarqueeTextView_marqueeSpeed , 6);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setAntiAlias(true);

        mTargetRect = new Rect();
        fontMetrics = mPaint.getFontMetricsInt();
        height = fontMetrics.bottom - fontMetrics.top + getPaddingBottom() + getPaddingTop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int top = getPaddingTop();
        int bottom = top + fontMetrics.bottom - fontMetrics.top;
        int left = getPaddingLeft() + mOffset;
        int right = left + mTextWidth;

        mTargetRect.set(left, top, right, bottom);
        mBaseLine = (mTargetRect.bottom + mTargetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mContent == null || mTargetRect == null) {
            return;
        }
        mTargetRect.left = getPaddingLeft() + mOffset;
        mTargetRect.right = mTargetRect.left + mTextWidth;
        canvas.drawText(mContent, mTargetRect.left, mBaseLine, mPaint);

    }

    public void setText(String text, final float width) {
        mOffset = 0;
        mContent = text;
        mTextWidth = (int) (mPaint.measureText(mContent));
        float distance = mTextWidth - width + getPaddingLeft() + getPaddingRight();
        LogUtil.logShow("mTextWidth = " + mTextWidth);
        LogUtil.logShow("width = " + width);
        mLongTag = distance > 0;
        if(mLongTag){
            mAnimator = ValueAnimator.ofInt(0, (int) distance);
            mAnimator.removeAllUpdateListeners();
            mAnimator.setDuration((long) (distance * mAnimSpeed));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    mOffset = -value;
                    invalidate();
                }
            });
        }
    }

    public void startAnim(LongTextListener listener) {
        this.mListener = listener;
        if(mAnimator != null){
            if (mAnimator.isRunning()) {
                return;
            }
            mAnimator.start();
        }
        if (mLongTag && mAnimator != null) {
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mListener.onAnimationEnd();
                }
            });
        } else {
            if(mAnimator != null){
                mAnimator.removeAllUpdateListeners();
                mAnimator.removeAllListeners();
            }
            mListener.onAnimationEnd();
        }
    }

    public interface LongTextListener{
        void onAnimationEnd();
    }

}
