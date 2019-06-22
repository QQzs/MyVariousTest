package com.zs.various.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zs.various.R
import kotlinx.android.synthetic.main.view_message.view.*
import org.jetbrains.anko.dip

/**
 * @Author: zs
 * @Date: 2019-05-28 15:19
 *
 * @Description: 消息更新
 */
class MessageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr){

    private var view: View? = null
    private var mTextHeight = 0f

    private var mAnimatorSet: AnimatorSet? = null
    private var mAnimatorTransition1: ObjectAnimator? = null
    private var mAnimatorTransition2: ObjectAnimator? = null
    private var mAnimatorTransition3: ObjectAnimator? = null
    private var mAnimatorAlpha1: ObjectAnimator? = null
    private var mAnimatorAlpha2: ObjectAnimator? = null

    private var newMessage = 3
    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                1 -> {
                    update("message ${newMessage ++}")
                    sendEmptyMessageDelayed(1,700)
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

    /**
     * 初始化动画
     */
    private fun initAnim(){
        mAnimatorSet = AnimatorSet()
        mAnimatorTransition1 = ObjectAnimator.ofFloat(tv_msg_one, View.TRANSLATION_Y, 0f, - mTextHeight)
        mAnimatorAlpha1 = ObjectAnimator.ofFloat(tv_msg_one, View.ALPHA, 0.8f , 0f)
        mAnimatorTransition2 = ObjectAnimator.ofFloat(tv_msg_two, View.TRANSLATION_Y, 0f, - mTextHeight)
        mAnimatorTransition3 = ObjectAnimator.ofFloat(tv_msg_three, View.TRANSLATION_Y, mTextHeight + dip(10), 0f)
        mAnimatorAlpha2 = ObjectAnimator.ofFloat(tv_msg_three, View.ALPHA, 0.2f , 1f)
        mAnimatorSet?.addListener(object : Animator.AnimatorListener{

            override fun onAnimationStart(p0: Animator?) {
                tv_msg_three?.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animator?) {

                tv_msg_one?.translationY = 0f
                tv_msg_one?.alpha = 1.0f
                tv_msg_two?.translationY = 0f
                tv_msg_three?.translationY = 0f
                tv_msg_three?.visibility = View.GONE

                tv_msg_one?.text = tv_msg_two?.text
                tv_msg_two?.text = tv_msg_three?.text

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        })
        mAnimatorSet?.duration = 400
        mAnimatorSet?.playTogether(mAnimatorTransition1 ,mAnimatorAlpha1 , mAnimatorTransition2 ,mAnimatorTransition3 , mAnimatorAlpha2)
    }

    /**
     * 刷新消息显示
     */
    fun update(message: String){
        tv_msg_three?.text = message
        mAnimatorSet?.let {
            if(!it.isRunning){
                it.start()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimatorSet?.let {
            mAnimatorSet = null
            mAnimatorTransition1 = null
            mAnimatorTransition2 = null
            mAnimatorTransition3 = null
            mAnimatorAlpha1 = null
            mAnimatorAlpha2 = null
        }
        mHandler?.removeCallbacksAndMessages(null)
    }

}