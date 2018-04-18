package com.zs.various.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 *
 * @author zs
 * @date 2017/12/18
 */
public class TextLineSpan extends ReplacementSpan {

    private Context mContext;
    private int mWidth;

    public TextLineSpan(Context context) {
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
        paint.setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawText(text, start, end, x, y, paint);
    }
}
