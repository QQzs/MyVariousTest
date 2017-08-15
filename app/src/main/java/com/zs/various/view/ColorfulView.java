package com.zs.various.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zs.various.R;
import com.zs.various.util.BezierUtil;

import java.util.Random;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:17
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorfulView extends FrameLayout {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mCopyBitmap;

    private int mScreenWidth;
    private int mScreenHeight;

    private Point mStartPoint;
    private Point mEndPoint;
    private Point mConOnePoint;
    private Point mConTwoPoint;

    private Random mRandom;
    protected int[] mColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW};


    /**
     * handler
     **/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                }
            super.handleMessage(msg);
        }
    };

    public ColorfulView(Context context) {
        this(context,null);
    }

    public ColorfulView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorfulView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
        mRandom = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
//        mStartPoint = new Point(mScreenWidth / 2, 0);
//        mEndPoint = new Point(mScreenWidth / 2, mScreenHeight);
//        mConOnePoint = new Point(mScreenWidth, mScreenHeight * 3 / 4);
//        mConTwoPoint = new Point(0, mScreenHeight / 4);

        mHandler.postDelayed(runnable,600);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mStartPoint = new Point((int)(mRandom.nextFloat() * mScreenWidth),-100);
            mEndPoint = new Point((int)(mRandom.nextFloat() * mScreenWidth),mScreenHeight + 50);
            mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
            mConTwoPoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
            addMyView();
            mHandler.postDelayed(this, 600);
        }
    };

    /**
     * 初始化动画
     * @param imageView
     */
    private void initAnimation(final ImageView imageView){

        Point conOnePoint = this.mConOnePoint;
        Point conTwoPoint = this.mConTwoPoint;
        Point startPoint = this.mStartPoint;
        Point endPoint = this.mEndPoint;

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(conOnePoint,conTwoPoint),startPoint,endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ColorfulView.this.removeView(imageView);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"alpha",1.0f,0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(6000);
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.start();

    }

    class MyTypeEvaluator implements TypeEvaluator<Point>{

        private Point onePoint;
        private Point twoPoint;

        public MyTypeEvaluator(Point onePoint, Point twoPoint) {
            this.onePoint = onePoint;
            this.twoPoint = twoPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            //利用三阶贝塞尔曲线公式算出中间点坐标
            return BezierUtil.CalculateBezierPointForCubicPoint(t,startValue,onePoint,twoPoint,endValue);
        }
    }

    /**
     * 添加view
     */
    private void addMyView(){
        mCopyBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mCopyBitmap);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        canvas.drawColor(mColors[mRandom.nextInt(mColors.length)], PorterDuff.Mode.SRC_IN);
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(mCopyBitmap);
//        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.addRule(CENTER_HORIZONTAL);
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(imageView,params);
        initAnimation(imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mStartPoint = new Point((int)(mRandom.nextFloat() * mScreenWidth),-100);
        mEndPoint = new Point((int)(mRandom.nextFloat() * mScreenWidth),mScreenHeight + 50);
        mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
        mConTwoPoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));

        addMyView();
        return true;
    }
}
