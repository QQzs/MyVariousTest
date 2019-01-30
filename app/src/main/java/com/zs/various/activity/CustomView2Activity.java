package com.zs.various.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;

/**
 * Created by zs
 * Date：2018年 12月 25日
 * Time：10:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class CustomView2Activity extends BaseActivity {

    private ImageView iv_img;

    float downX = 0;
    float downY = 0;
    private Canvas mCanvas;
    private Paint mPaint;
    private Point mPoint = new Point();

    float movingX = 0;
    float movingY = 0;
    private long lastTimestamp = 0L;//最后一次的时间戳

    @Override
    protected int setLayoutId() {
        return R.layout.activity_custom_view2;
    }

    @Override
    public void initView() {

        iv_img = findViewById(R.id.iv_img);

        iv_img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastTimestamp = System.currentTimeMillis();
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        movingX = event.getX();
                        movingY = event.getY();
                        long curTimestamp = System.currentTimeMillis();
                        //计算时间差
                        long detaT = curTimestamp - lastTimestamp;
                        //计算距离差
                        double detaS = Math.sqrt(Math.abs((movingX - downX) * (movingX - downX) + (movingY - downY) * (movingY - downY)));
//                    float detaS = Logic.disPos2d(movingX, movingY, downX, downY);
                        //由于速度是 px/ms
                        double v = detaS / detaT;
                        //速度转化为画笔宽度的等式
                        float width = 14/(float)v;
//                    L.d(width + L.l());
                        //限制极值情况
                        if ((width > 0) && width < 30) {
                            mPaint.setStrokeWidth(width);
                        }
                        mCanvas.drawLine(downX, downY, movingX, movingY, mPaint);
                        iv_img.invalidate();
                        downX = movingX;
                        downY = movingY;
                        lastTimestamp = curTimestamp;//更新时间
//                    movePos.add(new PointF(event.getX(), event.getY()));
                        break;
                }
                return true;
            }
        });

    }



    @Override
    public void initData() {

        //获取屏幕尺寸
        getWindowManager().getDefaultDisplay().getSize(mPoint);

        //创建一个和屏幕一样大的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(mPoint.x, mPoint.y, Bitmap.Config.ARGB_8888);
        //创建Canvas对象
        mCanvas = new Canvas(bitmap);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        //将bitmap用ImageView展示
        iv_img.setImageBitmap(bitmap);


    }

}
