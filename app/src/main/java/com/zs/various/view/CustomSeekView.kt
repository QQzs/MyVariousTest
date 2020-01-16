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
import com.zs.various.util.LogUtil
import org.jetbrains.anko.dip
import kotlin.math.max
import kotlin.math.min

/**
 * @Author: zs
 * @Date: 2020-01-07 18:54
 *
 * @Description: 剪切音乐
 */
class CustomSeekView @JvmOverloads constructor(context: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

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

    private var mListener: SeekListener? = null

    init {
        var typeArray = context.obtainStyledAttributes(attrs , R.styleable.CustomSeekView)
        mLineHeight = typeArray.getDimension(R.styleable.CustomSeekView_seekHeight , mLineHeight)
        mBackColor = typeArray.getColor(R.styleable.CustomSeekView_seekBackColor , mBackColor)
        mProgressColor = typeArray.getColor(R.styleable.CustomSeekView_seekProgressColor , mProgressColor)
        mProgress = typeArray.getFloat(R.styleable.CustomSeekView_seekProgress , mProgress)
        mDotColor = typeArray.getColor(R.styleable.CustomSeekView_seekDotColor , mDotColor)
        mDotRadius = typeArray.getDimension(R.styleable.CustomSeekView_seekDotRadius , mDotRadius)
        typeArray.recycle()

        mPaint = Paint()
        mPaint?.color = Color.BLUE
        mPaint?.style = Paint.Style.FILL
        mPaint?.strokeWidth = mLineHeight
        mPaint?.isAntiAlias = true

        mBackRectF = RectF()
        mProgressRectF = RectF()
    }
    
    private fun initRectF(){
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
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY){
            var statusBarHeight = dip(30)
            var newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(statusBarHeight), View.MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat() - paddingLeft - paddingRight
        mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (paddingLeft == 0){
            setPadding((mDotRadius / 2).toInt() , 0 , (mDotRadius / 2).toInt() , 0)
        }
        mWidth = w.toFloat() - paddingLeft - paddingRight
        mHeight = h.toFloat()
        initRectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint?.color = mBackColor
        canvas?.drawRoundRect(mBackRectF, mLineHeight / 2, mLineHeight / 2, mPaint)

        mPaint?.color = mProgressColor
        canvas?.drawRoundRect(mProgressRectF, mLineHeight / 2, mLineHeight / 2, mPaint)

        mPaint?.color = mDotColor
        canvas?.drawCircle(mProgressRectF.right + mDotRadius / 2, + mProgressRectF.top + mDotRadius / 2, mDotRadius, mPaint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(!isEnabled){
            return true
        }
        event?.let {
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    getProgress(event.x)
                }
                MotionEvent.ACTION_MOVE ->{
                    getProgress(event.x)
                }
                else ->{}
            }
        }
        return true
    }

    fun setSeekScroll(flag: Boolean){
        isEnabled = flag
    }

    /**
     * 设置Enable
     */
    fun setSeekStatus(flag: Boolean){
        mSeekStatus = flag
        isEnabled = flag
        alpha = if (flag){
            1.0f
        }else{
            0.5f
        }
    }

    private fun getProgress(downX: Float){
        var currentX = max(downX , mBackRectF.left)
        currentX = min(currentX , mBackRectF.right - mDotRadius)
        mProgressRectF.right = currentX
        var progress = (mProgressRectF.right - paddingLeft) / (mWidth - mDotRadius)
        progress = min(progress , 1.0f)
        if (mProgress != progress){
            mProgress = progress
            LogUtil.logShow("progress = $progress")
            mListener?.backProgress(mProgress)
            invalidate()
        }
    }

    fun setProgress(progress: Float){
        mProgress = progress
        mProgressRectF.right = paddingLeft.toFloat() + mWidth * mProgress
        mListener?.backProgress(mProgress)
        invalidate()
    }

    /**
     * 设置监听
     */
    fun setSeekListener(listener: SeekListener?){
        mListener = listener
    }

    interface SeekListener{
        fun backProgress(progress: Float)
    }

}