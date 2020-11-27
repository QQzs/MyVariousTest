package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.zs.various.R;
import com.zs.various.util.LogUtil;

public class LongTextView extends TextView {

    /**
     * 默认滚动速度
     */
    private static final float DEFAULT_SCROLL_SPEED = 4f;

    /**
     * 滚动器
     */
    private Scroller mScroller;

    /**
     * 是否停止
     */
    private boolean mIsStop;

    /**
     * view 宽度
     */
    private float mViewWidth = 1f;

    /**
     * 滚动速速
     */
    private float mSpeed;

    /**
     * 滚动结束监听
     */
    private LongTextListener mListener;

    public LongTextView(Context context) {
        this(context, null);
    }

    public LongTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LongTextView);
        mSpeed = typedArray.getFloat(R.styleable.LongTextView_longTextViewSpeed, DEFAULT_SCROLL_SPEED);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mViewWidth = getWidth();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 开始滚动
     */
    public void startScroll(LongTextListener listener) {
        this.mListener = listener;
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        // 获取文字真实长度
        float textLength = getPaint().measureText(text);
        if (textLength > mViewWidth) {
            // 设置水平滚动
            setHorizontallyScrolling(true);
            if (mScroller == null) {
                mScroller = new Scroller(getContext(), new LinearInterpolator());
                setScroller(mScroller);
            }
            // 重置状态
            mScroller.startScroll(0, 0, 0, 0, 0);
            mIsStop = false;
            // 获取滚动距离
            int distance = (int) (textLength + getPaddingLeft() + getPaddingRight() - mViewWidth);
            mScroller.startScroll(0, 0, distance, 0, (int) (distance * mSpeed));
            invalidate();
        } else {
            if (mListener != null) {
                mListener.onAnimationEnd();
            }
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller == null) {
            return;
        }

        if (mScroller.isFinished() && !mIsStop) {
            // 滚动结束
            mIsStop = true;
            LogUtil.logShow("computeScroll stop");
            if (mListener != null) {
                mListener.onAnimationEnd();
            }
        }
    }

    public interface LongTextListener{
        void onAnimationEnd();
    }

}
