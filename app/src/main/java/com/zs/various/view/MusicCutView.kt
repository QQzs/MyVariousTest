package com.zs.various.view

import android.content.Context
import android.graphics.*
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
 * @Description: 剪切音乐
 */
class MusicCutView @JvmOverloads constructor(context: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mWidth: Float = 0f
    private var mLineHeight: Float = 6f
    private var mTopPadding: Float = 20f
    private var mDotRadius: Float = 8f
    private var mProgressBitmap: Bitmap
    private var mCutLength: Float = 0f

    private var mBackRectF: RectF
    private var mCutRectF: RectF

    private var mBackColor: Int = Color.GRAY
    private var mCutColor: Int = Color.WHITE
    private var mDotColor: Int = Color.GREEN

    private var mListener: MusicCutListener? = null

    init {
        var typeArray = context.obtainStyledAttributes(attrs , R.styleable.MusicCutView)
        mLineHeight = typeArray.getDimension(R.styleable.MusicCutView_progressHeight , mLineHeight)
        mBackColor = typeArray.getColor(R.styleable.MusicCutView_progressColor , mBackColor)
        mCutColor = typeArray.getColor(R.styleable.MusicCutView_progressCutColor , mCutColor)
        mDotColor = typeArray.getColor(R.styleable.MusicCutView_progressDotColor , mDotColor)
        mDotRadius = typeArray.getDimension(R.styleable.MusicCutView_progressDotRadius , mDotRadius)
        var iconRes = typeArray.getResourceId(R.styleable.MusicCutView_progressIcon , R.mipmap.ic_progress_icon)
        typeArray.recycle()

        mPaint = Paint()
        mPaint?.color = Color.BLUE
        mPaint?.style = Paint.Style.FILL
        mPaint?.strokeWidth = mLineHeight
        mPaint?.isAntiAlias = true

        mBackRectF = RectF()
        mCutRectF = RectF()
        mProgressBitmap = BitmapFactory.decodeResource(resources, iconRes)
    }
    
    private fun initRectF(){
        mBackRectF.left = paddingLeft.toFloat()
        mBackRectF.top = mTopPadding + paddingTop
        mBackRectF.right = paddingLeft + mWidth
        mBackRectF.bottom = mLineHeight + mTopPadding + paddingTop

        mCutRectF.left = paddingLeft.toFloat()
        mCutRectF.top = mTopPadding + paddingTop
        mCutRectF.right = paddingLeft.toFloat() + mCutLength
        mCutRectF.bottom = mLineHeight + mTopPadding + paddingTop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY){
            var statusBarHeight = dip(60)
            var newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(statusBarHeight), View.MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat() - paddingLeft - paddingRight
        initRectF()
    }

    /**
     * 设置剪切的progress
     */
    fun setCutProgress(backDuration: Long , cutDuration: Long , listener: MusicCutListener? = null){
        mCutLength = mWidth / backDuration * cutDuration
        mListener = listener
        getProgress(0f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint?.color = mBackColor
        canvas?.drawRoundRect(mBackRectF, mLineHeight / 2, mLineHeight / 2, mPaint)

        mPaint?.color = mCutColor
        canvas?.drawRoundRect(mCutRectF, mLineHeight / 2, mLineHeight / 2, mPaint)

        mPaint?.color = mDotColor
        canvas?.drawCircle(mCutRectF.left, mTopPadding + mLineHeight / 2, mDotRadius, mPaint)

        canvas?.drawBitmap(mProgressBitmap , mCutRectF.left - mProgressBitmap.width / 2 , mCutRectF.bottom + mDotRadius ,  mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    getProgress(event.rawX)
                }
                MotionEvent.ACTION_MOVE ->{
                    getProgress(event.rawX)
                }
                MotionEvent.ACTION_UP ->{
                    var progress = (mCutRectF.left - paddingLeft) / mWidth
                    mListener?.backProgress(progress)
                }
                else ->{}
            }
        }
        return true
    }

    fun getProgress(downX: Float){
        var currentX = max(downX , mBackRectF.left)
        currentX = min(currentX , mBackRectF.right)
        mCutRectF.left = currentX
        mCutRectF.right = min(currentX + mCutLength , mBackRectF.right)
        invalidate()
    }

    interface MusicCutListener{
        fun backProgress(progress: Float)
    }

}