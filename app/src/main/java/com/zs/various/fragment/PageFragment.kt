package com.zs.various.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaeger.library.StatusBarUtil
import com.zs.various.R
import kotlinx.android.synthetic.main.fragment_pager_item.*
import java.util.*

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：11:20
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class PageFragment : androidx.fragment.app.Fragment() {

    var containerView: View? = null

    companion object {

        fun getInstance(num: Int): PageFragment{
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt("num", num)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var num = arguments?.getInt("num")
        Log.d("My_Log","onCreateView  $num")
        containerView = inflater.inflate(R.layout.fragment_pager_item, null, false)
        return containerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var num = arguments?.getInt("num")
        Log.d("My_Log","onViewCreated  -------  $num")
        tv_pager_num?.text = "page = $num"
        if (num == 0){
            iv_head_img?.visibility = View.VISIBLE
            view_status_bar?.visibility = View.GONE
        }else{
            iv_head_img?.visibility = View.GONE
            view_status_bar?.visibility = View.VISIBLE
            val color = -0x1000000 or Random().nextInt(0xffffff)
            view_status_bar?.setBackgroundColor(color)
        }

        btn_set_light_mode?.setOnClickListener {
            StatusBarUtil.setLightMode(activity)
        }

        btn_set_dark_mode?.setOnClickListener {
            StatusBarUtil.setDarkMode(activity)
        }
    }

}
