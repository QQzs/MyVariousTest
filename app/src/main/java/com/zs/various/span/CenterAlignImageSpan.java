package com.zs.various.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.text.style.ImageSpan;

/**
 * Created by zs
 * Date：2018年 10月 17日
 * Time：15:48
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class CenterAlignImageSpan extends ImageSpan {

    public CenterAlignImageSpan(Drawable drawable) {
        super(drawable);

    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        //计算y方向的位移
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;
        canvas.save();
        //绘制图片位移一段距离
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
