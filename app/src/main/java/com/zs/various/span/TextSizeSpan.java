package com.zs.various.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import com.zs.various.util.DensityUtil;

/**
 *
 * @author zs
 * @date 2017/12/18
 */
public class TextSizeSpan extends ReplacementSpan {

    private Context mContext;
    private int mWidth;

    public TextSizeSpan(Context context) {
        this.mContext = context;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //return text with relative to the Paint
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //draw the frame with custom Paint
        paint.setTextSize(DensityUtil.dip2px(mContext,15f));
        canvas.drawText(text, start, end, x, y, paint);
    }
}
