package com.zs.various.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.zs.various.R
import org.jetbrains.anko.dip


/**
 * 圆形的进度条
 */
class RecordView : View {

    private var mPaint: Paint? = null
    private var mBackColor: Int = 0
    private var mProgressColor: Int = 0
    private var mFrontColor: Int = 0
    private var mFrontCancelColor: Int = 0
    private var mBorderWidth: Float = 0f
    private var mCenterWidth: Float = 0f

    private var mMax: Float = 60f
    private var mValue: Float = 0f
    private var mStartPos = -90f

    private var mAnimator: ValueAnimator? = null

    constructor(context: Context) : super(context, null) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordView)
        mBackColor = mTypedArray.getColor(R.styleable.RecordView_recordBackColor, Color.GRAY)
        mFrontColor = mTypedArray.getColor(R.styleable.RecordView_recordFrontColor, Color.BLUE)
        mFrontCancelColor = mTypedArray.getColor(R.styleable.RecordView_recordFrontCancelColor, Color.BLUE)
        mBorderWidth = mTypedArray.getDimension(R.styleable.RecordView_recordBorderWidth, dip(6f).toFloat())
        mCenterWidth = mTypedArray.getDimension(R.styleable.RecordView_recordCenterWidth, dip(70f).toFloat())
        mTypedArray.recycle()
        initData()
    }

    fun setMax(max: Int){
        mMax = max.toFloat()
    }

    private fun initData(){
        mPaint = Paint()
        mPaint?.color = mBackColor
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = mBorderWidth
        mPaint?.isAntiAlias = true
        mProgressColor = mFrontColor

        mAnimator = ValueAnimator.ofFloat(0f , 1f)
        mAnimator?.interpolator = LinearInterpolator()
        mAnimator?.duration = (mMax * 1000).toLong()
        mAnimator?.addUpdateListener { animation ->
            val curValue = animation?.animatedValue as Float
            setProgress(curValue)
        }

    }

    /**
     * 恢复原始状态
     */
    fun resetStatus(){
        mValue = 0f
        setRecordStatus(true)
    }

    /**
     * 设置状态
     */
    fun setRecordStatus(record: Boolean){
        mProgressColor = if (record){
            mFrontColor
        }else{
            mFrontCancelColor
        }
        invalidate()
    }

    fun startAnim(){
        mAnimator?.start()
    }

    fun stopAnim(){
        mAnimator?.cancel()
    }

    /**
     * 设置进度
     */
    fun setProgress(newValue: Float){
        mValue = Math.max(0f, newValue)
        mValue = Math.min(mMax, newValue)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint?.color = mBackColor
        mPaint?.style = Paint.Style.STROKE
        //背景圆环
        val center = width * 0.5f
        val radius = center - mBorderWidth / 2
        canvas.drawCircle(center, center, radius, mPaint)

        //进度圆环
        mPaint?.color = mProgressColor
        if (mValue > 0) {
            val oval = RectF((center - radius), center - radius, center + radius, center + radius)
            val angle = 360 * mValue
            canvas.drawArc(oval, mStartPos, angle, false, mPaint)
        }

        // 内部圆
        val centerRadius = mCenterWidth / 2
        mPaint?.style = Paint.Style.FILL
        canvas.drawCircle(center, center, centerRadius, mPaint)
    }
}
