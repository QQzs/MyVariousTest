package com.zs.various.view

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import com.zs.various.R
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.view_message.view.*
import org.jetbrains.anko.dip

/**
 * @Author: zs
 * @Date: 2019-06-22 16:04
 *
 * @Description:
 */
class MessageView2 @JvmOverloads constructor(context: Context , attrs: AttributeSet? = null , defStyle: Int = 0): FrameLayout(context , attrs , defStyle) {

    private var view: View? = null
    private var mTextHeight = 0f

    private var mDuration = 2000L

    private var mAnimatorSet1: AnimationSet? = null
    private var mAnimatorSet2: AnimationSet? = null

    private var mAnimatorTransition1: TranslateAnimation? = null
    private var mAnimatorTransition2: TranslateAnimation? = null
    private var mAnimatorTransition3: TranslateAnimation? = null
    private var mAnimatorAlpha1: AlphaAnimation? = null
    private var mAnimatorAlpha2: AlphaAnimation? = null

    private var newMessage = 3
    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                1 -> {
                    startAnim("message ${newMessage ++}")
//                    sendEmptyMessageDelayed(1,700)
                }

            }
        }
    }

    init {
        view = LayoutInflater.from(context).inflate(R.layout.view_message, this)

        tv_msg_one?.post {
            mTextHeight = tv_msg_one.measuredHeight.toFloat()
            initAnim()
        }
        mHandler?.sendEmptyMessageDelayed(1,700)
    }

    fun initAnim(){

        mAnimatorSet1 = AnimationSet(false)
        mAnimatorTransition1 = TranslateAnimation(0f , 0f, 0f , -mTextHeight / 2)
        mAnimatorTransition1?.fillAfter = false
        mAnimatorAlpha1 = AlphaAnimation(0.8f , 0f)
        mAnimatorAlpha1?.fillAfter = false
        mAnimatorSet1?.addAnimation(mAnimatorTransition1)
        mAnimatorSet1?.addAnimation(mAnimatorAlpha1)
        mAnimatorSet1?.duration = mDuration
        mAnimatorSet1?.fillAfter = false

        mAnimatorTransition2 = TranslateAnimation(0f , 0f, 0f , -mTextHeight)
        mAnimatorTransition2?.duration = mDuration
        mAnimatorTransition2?.fillAfter = false


        mAnimatorSet2 = AnimationSet(false)
        mAnimatorTransition3 = TranslateAnimation(0f , 0f, mTextHeight + dip(10) , 0f)
        mAnimatorAlpha2 = AlphaAnimation(0.2f , 1f)
        mAnimatorSet2?.addAnimation(mAnimatorTransition3)
        mAnimatorSet2?.addAnimation(mAnimatorAlpha2)
        mAnimatorSet2?.duration = mDuration
        mAnimatorSet2?.fillAfter = false

        mAnimatorSet2?.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                LogUtil.logShow("33 ==== onAnimationEnd")

                tv_msg_one?.translationY = 0f
                tv_msg_one?.alpha = 1.0f
                tv_msg_two?.translationY = 0f
                tv_msg_three?.translationY = 0f

                tv_msg_one?.text = tv_msg_two?.text
                tv_msg_two?.text = tv_msg_three?.text
                tv_msg_three?.visibility = View.GONE

            }

            override fun onAnimationStart(p0: Animation?) {

                tv_msg_three?.visibility = View.VISIBLE
            }

        })


    }

    fun startAnim(msg: String){

        tv_msg_three?.text = "3333333333333333"

        if (mAnimatorSet1 == null){
            initAnim()
        }

        tv_msg_one?.startAnimation(mAnimatorSet1)
        tv_msg_two?.startAnimation(mAnimatorTransition2)
        tv_msg_three?.startAnimation(mAnimatorSet2)
    }

    fun stopAnim(){

        mAnimatorSet1
        mAnimatorSet1?.setAnimationListener(null)
        mAnimatorSet1?.cancel()
        mAnimatorSet1 = null
        mAnimatorSet2?.setAnimationListener(null)
        mAnimatorSet1?.cancel()
        mAnimatorSet1 = null

        tv_msg_one?.clearAnimation()
        tv_msg_two?.clearAnimation()
        tv_msg_three?.clearAnimation()

        mHandler?.removeCallbacksAndMessages(null)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnim()
    }

}
