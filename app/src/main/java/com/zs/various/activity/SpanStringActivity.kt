package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.UnderlineSpan
import com.zs.various.R
import com.zs.various.span.CenterAlignImageSpan
import com.zs.various.span.TextLineSpan
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
        addTextLine()
        setSpanImage()

    }

    /**
     * 设置不同颜色文字
     */
    private fun setForegroundColor() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_span_1?.text = spannableString
    }

    /**
     * 设置背景色
     */
    private fun setBackgroundColor() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(BackgroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_span_2?.text = spannableString
    }

    /**
     * 设置超链接
     */
    private fun setLink() {
        val spannableString = SpannableString(mContent)
        //设置下划线
        spannableString.setSpan(UnderlineSpan(), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_span_3?.text = spannableString
    }

    /**
     * 改变文字大小
     */
    private fun changeTextSize() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(TextSizeSpan(this), 5, mContent!!.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        tv_span_4?.text = spannableString
    }

    /**
     * 中划线
     */
    private fun addTextLine() {
        val spannableString = SpannableString(mContent)
        spannableString.setSpan(TextLineSpan(this), 0,5, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        tv_span_5?.text = spannableString
    }

    /**
     * 图标
     */
    private fun setSpanImage(){

        var drawable = ContextCompat.getDrawable(this,R.mipmap.ic_chat_img)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        var imageSpan = CenterAlignImageSpan(drawable)
        var str1 = "添加这个是什么的的的短款什么的的的短款的款式多的肯定是多的肯定多的肯定的说到底的时刻山东省开始"
        var str2 = "    "
        var str = str2 + str1
        val spannableString = SpannableString(str)

//        spannableString.setSpan(imageSpan,str.length - str2.length , str.length , ImageSpan.ALIGN_BASELINE)

        spannableString.setSpan(imageSpan,0 , 2 , ImageSpan.ALIGN_BASELINE)

        tv_span_6?.text = spannableString

    }

}
