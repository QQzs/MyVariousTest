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
import com.zs.various.bean.MessageBean
import com.zs.various.util.extension.drawLeft
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
    private var mTextHeight = dip(20f).toFloat()

    private var mAnimatorSet: AnimatorSet? = null
    private var mAnimatorTransition1: ObjectAnimator? = null
    private var mAnimatorTransition2: ObjectAnimator? = null
    private var mAnimatorTransition3: ObjectAnimator? = null
    private var mAnimatorAlpha1: ObjectAnimator? = null
    private var mAnimatorAlpha2: ObjectAnimator? = null

    private var mMessageList = mutableListOf<MessageBean>()

    private var newMessage = 3
    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                1 -> {
                    update("message ${newMessage ++}")
                    sendEmptyMessageDelayed(1,1500)
                }

            }
        }
    }


    init {
        view = LayoutInflater.from(context).inflate(R.layout.view_message, this)

//        tv_msg_one?.post {
//            mTextHeight = tv_msg_one.measuredHeight.toFloat()
//            initAnim()
//        }

        mMessageList.add(MessageBean("message === 1"))
        mMessageList.add(MessageBean(1 , "message === 2"))
        mMessageList.add(MessageBean("message === 3"))
        mMessageList.add(MessageBean("message === 4"))
        mMessageList.add(MessageBean("message === 5"))
        mMessageList.add(MessageBean(1 , "theme ========= "))

        initAnim()
        switchMessage()

//        mHandler?.sendEmptyMessageDelayed(1,1500)
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

                var bean = mMessageList[0]
                mMessageList.removeAt(0)
                mMessageList.add(bean)

                switchMessage()


            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        })
        mAnimatorSet?.duration = 1000
        mAnimatorSet?.playTogether(mAnimatorTransition1 ,mAnimatorAlpha1 , mAnimatorTransition2 ,mAnimatorTransition3 , mAnimatorAlpha2)
    }


    fun switchMessage(){

        var bean1 = mMessageList[0]
        tv_msg_one?.text = bean1.message
        if (bean1.type == 0){
            tv_msg_one?.drawLeft(R.mipmap.home_bar_news_nor)
        }else{
            tv_msg_one?.drawLeft(R.mipmap.home_bar_news_sel)
        }

        var bean2 = mMessageList[1]
        tv_msg_two?.text = bean2.message
        if (bean2.type == 0){
            tv_msg_two?.drawLeft(R.mipmap.home_bar_news_nor)
        }else{
            tv_msg_two?.drawLeft(R.mipmap.home_bar_news_sel)
        }

        var bean3 = mMessageList[2]
        tv_msg_three?.text = bean3.message
        if (bean3.type == 0){
            tv_msg_three?.drawLeft(R.mipmap.home_bar_news_nor)
        }else{
            tv_msg_three?.drawLeft(R.mipmap.home_bar_news_sel)
        }

        mAnimatorSet?.start()

    }


    /**
     * 刷新消息显示
     */
    fun update(message: String){

        mAnimatorSet?.let {
            if(!it.isRunning){
                it.start()
            }
        }
    }

    /**
     * 刷新消息显示
     */
    fun update(bean: MessageBean){



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