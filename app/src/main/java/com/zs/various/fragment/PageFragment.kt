package com.zs.various.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import kotlinx.android.synthetic.main.fragment_pager_item.*

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：11:20
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class PageFragment : Fragment() {

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
        if (num == 3){
            iv_head_img?.visibility = View.VISIBLE
        }else{
            iv_head_img?.visibility = View.GONE
        }
    }

    fun getNeedOffView(): View?{
        return containerView?.findViewById(R.id.ll_num)
    }

}
