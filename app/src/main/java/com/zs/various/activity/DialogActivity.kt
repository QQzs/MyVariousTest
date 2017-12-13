package com.zs.various.activity

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.zs.various.R
import com.zs.various.fragment.MyDialogFragment
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        btn_1.setOnClickListener {
            showDialog()
        }

        btn_2.setOnClickListener {
            var dialogFragment = MyDialogFragment()
            dialogFragment.show(fragmentManager, "DISCOUNT")
        }
    }

    private fun showDialog(){
        var dialog = Dialog(this,R.style.MyDialogStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var view = View.inflate(this,R.layout.dialog_layout,null)
        dialog.setContentView(view)

        var window = dialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.decorView.setPadding(0, 0, 0, 0)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }
        dialog.show()

    }
}
