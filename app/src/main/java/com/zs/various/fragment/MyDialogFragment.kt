package com.zs.various.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.view.*
import com.zs.various.R

/**
 *
Created by zs
Date：2017年 12月 13日
Time：11:39
—————————————————————————————————————
About: Dialog
—————————————————————————————————————
 */
class MyDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var view = View.inflate(activity, R.layout.dialog_layout,null)
        var dialog = dialog
        var window = dialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.decorView.setPadding(0, 0, 0, 0)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }
//        window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        return view
    }
}