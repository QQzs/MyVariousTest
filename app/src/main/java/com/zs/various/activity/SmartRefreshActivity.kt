package com.zs.various.activity

import com.zs.various.R
import com.zs.various.adapter.ActivityAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_smart_reresh.*

/**
 * @Author: zs
 * @Date: 2019-08-17 16:01
 *
 * @Description:
 */
class SmartRefreshActivity: BaseActivity(){

    override fun setLayoutId() = R.layout.activity_smart_reresh

    override fun initView() {

    }

    override fun initData() {

        var acts = ArrayList<Class<*>>()
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)

        var adapter = ActivityAdapter(acts)
        RecyclerViewUtil.init(this , smart_recycler_view , adapter)

    }

}