package com.zs.various.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * @Author: zs
 * @Date: 2020-01-16 16:15
 *
 * @Description:
 */
class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): View(context , attrs , defStyle){

    private var mPaint: Paint? = null
    private var mSweepGradient: SweepGradient? = null
    private var mRectF: RectF? = null
    private var mCenter: Float = 0f
    private var mStrokeWidth: Float = 16f
    private var mRadius: Float = 50f
    private var mRotate: Float = 360f

    private var mAnimator: ValueAnimator? = null

    init {
        mPaint = Paint()
        mPaint?.color = Color.WHITE
        mPaint?.strokeCap = Paint.Cap.ROUND
        mPaint?.strokeWidth = mStrokeWidth
        mPaint?.isAntiAlias = true
        mPaint?.style = Paint.Style.STROKE

        mAnimator = ValueAnimator.ofFloat(360f , 0f)
        mAnimator?.duration = 500
        mAnimator?.repeatCount = ValueAnimator.INFINITE
        mAnimator?.addUpdateListener { value ->
            mRotate = value?.animatedValue as Float
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenter = width * 0.5f
        mSweepGradient = SweepGradient(mCenter ,mCenter , intArrayOf(Color.WHITE , Color.TRANSPARENT) , floatArrayOf(0f , 1f))
        mPaint?.shader = mSweepGradient
        mRadius = min(mCenter - mStrokeWidth , mRadius)
        mRectF = RectF(mCenter - mRadius, mCenter - mRadius , mCenter + mRadius, mCenter + mRadius)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.rotate(mRotate , mCenter , mCenter)
        canvas?.drawArc(mRectF, 10f, 300f, false, mPaint)
    }


    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE){
            mAnimator?.start()
        }else{
            mAnimator?.cancel()
        }
    }

}