package com.zs.various.view

import android.content.Context
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.widget.TextView
import com.zs.various.R

/**
 * @Author: zs
 * @Date: 2019-07-05 16:41
 *
 * @Description:
 */
class ExpandTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    lateinit var mText: String
    var mViewHeight: Int = 0
    var isExpand: Boolean = false
    var mLine: Int = 0
    var mShowLine: Int = 2

    fun initText(text: String?){
        text?.let {
            mLine = 0
            mText = it
            setText(text)
            post {
//                if (mLine == 0){
//                    mLine = lineCount
//                }
                mLine = lineCount
                mViewHeight = measuredHeight
                initLine()
            }
        }?: clearText()
    }

    private fun clearText(){
        text = ""
    }

    private fun initLine(){
        if(isExpand){
            setShowText("$mText\u3000 ", isExpand)
        }else{
            if (lineCount > mShowLine){
                var showText = StringBuilder()
                for ( i in 0 until mShowLine){
                    showText.append(mText.substring(layout.getLineStart(i) , layout.getLineEnd(i)))
                }
                var text = showText.toString()
                text = text.substring(0 , text.length - 3) + "...\u3000 "
                setShowText(text , isExpand)
            }

        }
        setOnClickListener {
            if (mLine > mShowLine){
                isExpand = !isExpand
                initLine()
            }
        }

    }

    private fun setShowText(textContent: String, expand: Boolean) {
        val showText = SpannableString(textContent)
        val drawable = ContextCompat.getDrawable(context, if (expand) R.mipmap.ic_text_close else R.mipmap.ic_text_open)
        drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        showText.setSpan(imageSpan, textContent.length - 2, textContent.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        text = showText
    }

}