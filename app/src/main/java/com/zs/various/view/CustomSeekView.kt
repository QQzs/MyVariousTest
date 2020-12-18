package com.zs.various.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zs.various.R
import org.jetbrains.anko.dip
import kotlin.math.max
import kotlin.math.min

/**
 * @Author: zs
 * @Date: 2020-01-07 18:54
 *
 * @Description:
 */
class CustomSeekView @JvmOverloads constructor(context: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mHeight: Float = 0f
    private var mWidth: Float = 0f
    private var mLineHeight: Float = 18f
    private var mDotRadius: Float = 20f
    private var mProgress: Float = 0f

    private var mBackRectF: RectF
    private var mProgressRectF: RectF

    private var mBackColor: Int = Color.GRAY
    private var mProgressColor: Int = Color.GREEN
    private var mDotColor: Int = Color.WHITE
    private var mSeekStatus: Boolean = true
    private var mDownScale: Boolean = false
    private var mScrolling: Boolean = false

    var seekProgressCallBack: ((Float) -> Unit)? = null
    var seekUpCallBack: ((Float) -> Unit)? = null
    var seekDownCallBack: (() -> Unit)? = null

    init {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekView)
        mLineHeight = typeArray.getDimension(R.styleable.CustomSeekView_seekHeight, mLineHeight)
        mBackColor = typeArray.getColor(R.styleable.CustomSeekView_seekBackColor, mBackColor)
        mProgressColor = typeArray.getColor(R.styleable.CustomSeekView_seekProgressColor, mProgressColor)
        mProgress = typeArray.getFloat(R.styleable.CustomSeekView_seekProgress, mProgress)
        mDotColor = typeArray.getColor(R.styleable.CustomSeekView_seekDotColor, mDotColor)
        mDotRadius = typeArray.getDimension(R.styleable.CustomSeekView_seekDotRadius, mDotRadius)
        mDownScale = typeArray.getBoolean(R.styleable.CustomSeekView_seekDownScale, mDownScale)
        typeArray.recycle()

        mPaint = Paint()
        mPaint?.color = Color.BLUE
        mPaint?.style = Paint.Style.FILL
        mPaint?.strokeWidth = mLineHeight
        mPaint?.isAntiAlias = true

        mBackRectF = RectF()
        mProgressRectF = RectF()
    }

    private fun initRectF() {
        mBackRectF.left = paddingLeft.toFloat()
        mBackRectF.top = (mHeight - mLineHeight) / 2
        mBackRectF.right = paddingLeft + mWidth
        mBackRectF.bottom = (mHeight + mLineHeight) / 2

        mProgressRectF.left = paddingLeft.toFloat()
        mProgressRectF.top = (mHeight - mLineHeight) / 2
        mProgressRectF.right = paddingLeft.toFloat() + mWidth * mProgress
        mProgressRectF.bottom = (mHeight + mLineHeight) / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var mode = MeasureSpec.getMode(heightMeasureSpec)
        if (mode != MeasureSpec.EXACTLY) {
            var statusBarHeight = dip(30)
            var newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(statusBarHeight), MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
            mHeight = statusBarHeight.toFloat()
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        }
        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat() - paddingLeft - paddingRight
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (paddingLeft == 0) {
            setPadding((mDotRadius / 2).toInt() + 1, 0, (mDotRadius / 2).toInt() + 1, 0)
        }
        mWidth = w.toFloat() - paddingLeft - paddingRight
        mHeight = h.toFloat()
        initRectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint?.color = mBackColor
        mPaint?.let {
            canvas?.drawRoundRect(mBackRectF, mLineHeight / 2, mLineHeight / 2, it)
            mPaint?.color = mProgressColor
            canvas?.drawRoundRect(mProgressRectF, mLineHeight / 2, mLineHeight / 2, it)
        }

        mPaint?.color = mDotColor


        if (mDotRadius == 0f)
            return
        var realRadius = mDotRadius
        if (mDownScale && mScrolling) {
            realRadius = mDotRadius * 1.5f
        }
        var circleX = mProgressRectF.right + realRadius / 2
        circleX = max(circleX, realRadius)
        circleX = min(circleX, paddingLeft + mWidth - realRadius)
        mPaint?.let {
            canvas?.drawCircle(circleX, mHeight / 2, realRadius, it)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) {
            return true
        }
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    seekDownCallBack?.invoke()
                    mScrolling = true
                    updateProgress(event.x)
                }
                MotionEvent.ACTION_MOVE -> {
                    mScrolling = true
                    updateProgress(event.x)
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    mScrolling = false
                    seekUpCallBack?.invoke(getProgress(event.x))
                    invalidate()
                }
                else -> {
                }
            }
        }
        return true
    }

    fun setSeekScroll(flag: Boolean) {
        isEnabled = flag
    }

    fun setDotRadio(radio: Float) {
        mDotRadius = radio
        invalidate()
    }

    /**
     * 设置Enable
     */
    fun setSeekStatus(flag: Boolean) {
        mSeekStatus = flag
        isEnabled = flag
        alpha = if (flag) {
            1.0f
        } else {
            0.5f
        }
    }

    private fun updateProgress(downX: Float) {
        var progress = getProgress(downX)
        if (mProgress != progress) {
            mProgress = progress
            seekProgressCallBack?.invoke(mProgress)
            invalidate()
        }
    }

    private fun getProgress(downX: Float): Float {
        var currentX = max(downX, mBackRectF.left)
        currentX = min(currentX, mBackRectF.right)
        mProgressRectF.right = currentX
        var progress = (mProgressRectF.right - paddingLeft) / (mWidth - mDotRadius)
        return min(progress, 1.0f)
    }

    fun setProgress(progress: Float) {
        if (!mScrolling) {
            mProgress = progress
            var right = paddingLeft.toFloat() + mWidth * mProgress
            mProgressRectF.right = min(right, mBackRectF.right)
            seekProgressCallBack?.invoke(mProgress)
            invalidate()
        }
    }

    fun getProgress(): Float {
        return mProgress
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent?.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }
}