package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import com.zs.various.R
import com.zs.various.span.TextSizeSpan
import kotlinx.android.synthetic.main.activity_span_string.*

class SpanStringActivity : AppCompatActivity() {

    var mContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span_string)

        mContent = "测试内容，后面的数据我们不一样。。。"
        setForegroundColor()
        setBackgroundColor()
        setLink()
        changeTextSize()
    }

    /**
     * 设置不同颜色文字
     */
    private fun setForegroundColor() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_span_1.text = spannableString
    }

    /**
     * 设置背景色
     */
    private fun setBackgroundColor() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(BackgroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_span_2.text = spannableString
    }

    /**
     * 设置超链接
     */
    private fun setLink() {
        val spannableString = SpannableString(mContent)
        //设置下划线
        spannableString.setSpan(UnderlineSpan(), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_span_3.text = spannableString
    }

    /**
     * 改变文字大小
     */
    private fun changeTextSize() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(TextSizeSpan(this), 5, mContent!!.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        tv_span_4.text = spannableString
    }
}
