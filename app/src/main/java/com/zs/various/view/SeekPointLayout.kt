package com.zs.various.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.zs.various.R
import kotlinx.android.synthetic.main.view_seek_point_layout.view.*
import org.jetbrains.anko.dip

/**
 * @Author: zs
 * @Date: 2020-01-13 19:57
 *
 * @Description:
 */
class SeekPointLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    companion object{
        const val POINT_STATUS_EDIT = 2000
        const val POINT_STATUS_PLAY = 2001
    }
    var mStatus = POINT_STATUS_EDIT
    var mCurrentPosition = -1
    var mVideoLength = 30L
    var mPointSize: Int = 0
    var mPointList = mutableListOf<PointModel>()
    var mPointViews = mutableListOf<TextView>()

    var mPointSelect: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_seek_point_layout, this)
        initView()
    }

    fun initView(){

        mPointList.add(PointModel(2.0))
        mPointList.add(PointModel(12.0))
        mPointList.add(PointModel(15.0 , true))
        mPointList.add(PointModel(20.0))
        mPointList.add(PointModel(23.0 , true))
        mPointList.add(PointModel(29.0))

        changeStatus(true)

        post {
            mPointSize = dip(25)
            var layoutParams = seek_view.layoutParams as LayoutParams
            layoutParams.leftMargin = mPointSize / 2
            layoutParams.rightMargin = mPointSize / 2
            seek_view.layoutParams = layoutParams
            for ((index , time) in mPointList.withIndex()){
                addPoint(index , time)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addPoint(index: Int, model: PointModel){
        if (model.start > mVideoLength){
            return
        }
        var left = ((width - mPointSize) * model.start / mVideoLength).toInt()
        var point = TextView(context)
        point.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        var params = LayoutParams(mPointSize , mPointSize)
        params.setMargins(left , (height - mPointSize) / 2 , 0 , 0)
        point.gravity = Gravity.CENTER
        point.layoutParams = params
        addView(point)
        mPointViews.add(point)
        setPointView(point , index)
        showAnim(point)

        point.setOnTouchListener(object : OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                if (mStatus == POINT_STATUS_EDIT){
                    if (event?.action == MotionEvent.ACTION_UP){
                        shakeAnim(point)
                        changeSelect(index , point)
                    }
                    return true
                }else{
                    return false
                }
            }
        })
    }

    fun setPointView(point: TextView , position: Int){
        if (position < 0 || position > mPointViews.size - 1){
            return
        }
        var model = mPointList[position]
        if (position == mCurrentPosition){
            if (model.complete){
                point.text = ""
                point.setBackgroundResource(R.mipmap.ic_complete_select)
            }else{
                point.text = (position + 1).toString()
                point.setTextColor(Color.WHITE)
                point.setBackgroundResource(R.drawable.bg_circle_green)
            }
        }else{
            if (model.complete){
                point.text = ""
                point.setBackgroundResource(R.mipmap.ic_complete_while)
            }else{
                point.text = (position + 1).toString()
                point.setTextColor(Color.BLACK)
                point.setBackgroundResource(R.drawable.bg_circle_white)
            }
        }
    }

    fun changeRecordStatus(){
        if (mCurrentPosition >= 0 && mCurrentPosition <= mPointViews.size - 1){
            mPointList[mCurrentPosition].complete = true
            changeSelect(mCurrentPosition , mPointViews[mCurrentPosition])
        }
    }

    fun changeStatus(isEdit: Boolean){
        if (isEdit){
            mStatus = POINT_STATUS_EDIT
            seek_view?.setSeekScroll(false)
        }else{
            mStatus = POINT_STATUS_PLAY
            seek_view?.setSeekScroll(true)
        }
    }

    fun changeSelect(position: Int , clickPoint: TextView){
        var lastPosition = mCurrentPosition
        mCurrentPosition = position
        if (lastPosition >= 0 && lastPosition <= mPointViews.size - 1){
            setPointView(mPointViews[lastPosition] , lastPosition)
        }
        setPointView(clickPoint , position)
    }

    private fun showAnim(point: View){
        var animatorSet = AnimatorSet()
        var alpha = ObjectAnimator.ofFloat(point , View.ALPHA , 0.1f , 1f)
        var scaleX = ObjectAnimator.ofFloat(point , View.SCALE_X , 0f , 1.3f , 0.9f , 1.2f , 1f)
        var scaleY = ObjectAnimator.ofFloat(point , View.SCALE_Y , 0f , 1.3f , 0.9f , 1.2f , 1f)
        animatorSet.duration = 1000
        animatorSet.playTogether(alpha , scaleX , scaleY)
        animatorSet.start()
    }

    private fun shakeAnim(point: View){
        var animatorSet = AnimatorSet()
        var scaleX = ObjectAnimator.ofFloat(point , View.SCALE_X , 0.8f , 1.3f , 0.9f , 1f)
        var scaleY = ObjectAnimator.ofFloat(point , View.SCALE_Y , 0.8f , 1.3f , 0.9f , 1f)
        animatorSet.duration = 300
        animatorSet.playTogether(scaleX , scaleY)
        animatorSet.start()
    }

    class PointModel{
        var start: Double = 0.0
        var complete: Boolean = false

        constructor(start: Double) {
            this.start = start
        }

        constructor(start: Double, complete: Boolean) {
            this.start = start
            this.complete = complete
        }
    }

}