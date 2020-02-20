package com.zs.various.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.*
import android.widget.PopupWindow
import com.zs.various.R
import com.zs.various.fragment.MyDialogFragment
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.dialog_layout.view.*

class DialogWithPopupActivity : androidx.fragment.app.FragmentActivity() {

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

        btn_3.setOnClickListener {
            showPopupwindow()
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

    private fun showPopupwindow(){
        val contentView = View.inflate(this,R.layout.dialog_layout,null)
        var popupWindow = PopupWindow(
                contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.animationStyle = R.style.top_slide_animation
        popupWindow.showAsDropDown(btn_3)
//        popupWindow.showAtLocation(btn_3 , Gravity.BOTTOM , 0 , 0)

        contentView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
            override fun onViewDetachedFromWindow(p0: View?) {

            }

            override fun onViewAttachedToWindow(p0: View?) {
                contentView.view_bottom?.visibility = View.VISIBLE
                var animator = ObjectAnimator.ofFloat(contentView.view_bottom , View.ALPHA , 0f , 0.6f)
                animator.startDelay = 250
                animator.duration = 300
                animator.start()
            }
        })
        contentView.view_bottom.setOnClickListener {
            var animator = ObjectAnimator.ofFloat(contentView.view_bottom , View.ALPHA , 0.6f , 0f)
            animator.duration = 300
            animator.start()
            contentView.view_bottom?.postDelayed({
                popupWindow.dismiss()
            },250)
        }

    }

}
