package com.zs.login.various.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zs.login.various.R;

import java.util.Random;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：13:56
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class HeartView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mCopyBitmap;
    private Random mRandom;

    protected int[] mColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW};


    public HeartView(Context context) {
        super(context);
        initView();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        Log.d("My_View","initView");

        mRandom = new Random();
        //源图像
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
        //创建一个目标图像，由于不能对getResources方法提取到的Bitmap对象进行修改操作，必须复制出一个
//        mCopyBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCopyBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Canvas基于目标图像进行操作
        Canvas canvas = new Canvas(mCopyBitmap);
        //在canvas上的mOutPutBitmap绘制
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.drawColor(mRandom.nextInt(mColors.length), PorterDuff.Mode.SRC_IN);

//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageBitmap(mCopyBitmap);
//        addView(imageView);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        Log.d("My_View","draw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mBitmap.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else{
            height = mBitmap.getHeight();
        }
        setMeasuredDimension(width, height);
    }

}
