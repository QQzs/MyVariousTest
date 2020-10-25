package com.zs.various.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.DensityUtil
import com.zs.various.util.LogUtil
import com.zs.various.view.flowlayout.adapter.FoldAdapter
import kotlinx.android.synthetic.main.activity_custom_view4.*
import kotlinx.android.synthetic.main.item_tag_view.view.*
import org.jetbrains.anko.dip

class CustomViewFourActivity(): BaseActivity(){

    var tagViews = mutableListOf<View>()

    var tagStrings = mutableListOf<String>()

    var adapter: FoldAdapter? = null

    var bgEnterAnimator: ObjectAnimator? = null
    var bgOutAnimator: ObjectAnimator? = null

    var textAnimator: ObjectAnimator? = null

    var screenWidth = 0f;
    var enterDuration = 1000L

    override fun setLayoutId() = R.layout.activity_custom_view4

    override fun initView() {

        adapter = FoldAdapter()
        flow_layout_mine?.setTagAdapter(adapter)
        btn_add?.setOnClickListener {
            var tag = et_tag.text.toString().trim()
            if (!TextUtils.isEmpty(tag)) {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = tag
                tagStrings.add(0 , tag)

            } else {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = "${tagViews.size} 我的数据"
                tagStrings.add(0 , "${tagStrings.size} 我的数据")

            }
            adapter?.addAll(tagStrings)
            et_tag.setText("")
        }

        btn_clear?.setOnClickListener {
            tagStrings.clear()
            adapter?.addAll(tagStrings)
        }

        btn_reset?.setOnClickListener {
            flow_layout_mine?.setFold(true)
        }

        btn_add_message?.setOnClickListener {
            var tag = et_tag.text.toString().trim()
            if (!TextUtils.isEmpty(tag)) {
                startAnim(tag)
            } else{
                var ss = "熊大爷给金主大大送了一梦的契约,woshiForegroundColorSpanyi,===== 12345678国"
                val spanString = SpannableString(ss)
                val fcs1 = ForegroundColorSpan(resources.getColor(R.color.color_1))
                spanString.setSpan(fcs1, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                startAnim(spanString)
            }
            et_tag.setText("")
        }

        screenWidth = DensityUtil.getDisplayWidth(this) * 1f

    }

    override fun initData() {

    }

    fun startAnim(text: CharSequence) {

        var bgWidth = screenWidth - 2 * dip(50)
        long_tv?.text = text
        fl_bg_anim?.translationX = - screenWidth
        long_tv?.translationX = 0f

        fl_bg_anim?.visibility = View.VISIBLE
        if (bgEnterAnimator == null) {
            bgEnterAnimator = ObjectAnimator.ofFloat(fl_bg_anim,"translationX", screenWidth , 0f)
            bgEnterAnimator?.duration = enterDuration
            bgEnterAnimator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    initTextAnim(text)
                }
            })
        }
        bgEnterAnimator?.start()

    }

    fun initOutAnim() {
        if (bgOutAnimator == null) {
            bgOutAnimator = ObjectAnimator.ofFloat(fl_bg_anim,"translationX", 0f , -screenWidth)
            bgOutAnimator?.duration = enterDuration
            bgOutAnimator?.startDelay = 1000
        }
        bgOutAnimator?.start()
        bgOutAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

            }
        })
    }

    fun initTextAnim(text: CharSequence) {
        var length = long_tv.paint.measureText(text.toString())
        var viewWidth = screenWidth - dip(20) * 2
        LogUtil.logShow("length = $length  viewWidth = $viewWidth")
        var distance = length + dip(40) + dip(10) - viewWidth
        LogUtil.logShow("distance = $distance")
        textAnimator = ObjectAnimator.ofFloat(long_tv,"translationX", 0f , -distance)
        textAnimator?.duration = 2000
        textAnimator?.start()
        textAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                initOutAnim()
            }
        })
    }

}