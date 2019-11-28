package com.zs.various.view

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.AppCompatTextView
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Gravity

/**
 * @Author: zs
 * @Date: 2019-11-18 14:27
 * @Description:
 */
class FitTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    private var mTextPaint: Paint? = null
    /**
     * 获取当前所设置文字大小作为最大文字大小
     */
    private var mMaxTextSize: Float = 0.toFloat()
    /**
     * 获取当前所设置文字大小作为最小文字大小
     */
    private val mMinTextSize = 10f

    init {
        // 默认水平居中
        gravity = gravity or Gravity.CENTER_VERTICAL
        setLines(1)
        initialise()
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        refitText(text.toString(), this.width)
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun initialise() {
        mTextPaint = TextPaint()
        mTextPaint!!.set(this.paint)
        // 最大的大小默认为特定的文本大小，除非它太小了
        mMaxTextSize = this.textSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw) {
            refitText(this.text.toString(), w)
        }
    }

    /**
     * Resize the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     *
     */
    private fun refitText(text: String, textWidth: Int) {
        if (textWidth > 0) {
            val availableWidth = textWidth - this.paddingLeft - this.paddingRight
            var trySize = mMaxTextSize
            mTextPaint?.textSize = trySize
            while (mTextPaint!!.measureText(text) > availableWidth) {
                trySize -= 1f
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize
                    break
                }
                mTextPaint?.textSize = trySize
            }
            // setTextSize参数值为sp值
            textSize = px2sp(context, trySize)
        }
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    private fun px2sp(context: Context, pxValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return pxValue / fontScale
    }

}
