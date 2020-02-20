package com.zs.various.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zs.various.R;
import com.zs.various.util.BezierUtil;
import com.zs.various.util.Constant;

import java.util.Random;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:28
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorfullBackActivity extends Activity {

    private FrameLayout backView;
    private ImageView iv_show;

    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mCopyBitmap;
    private Random mRandom;

    private int mScreenWidth;
    private int mScreenHeight;

    private Point mStartPoint;
    private Point mEndPoint;
    private Point mConOnePoint;
    private Point mConTwoPoint;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorfull_back_layout);
        backView = findViewById(R.id.back_view);
        iv_show = findViewById(R.id.iv_show);
        initView();
        mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        mHandler.postDelayed(runnable,600);


        Picasso.with(this)
                .load(Constant.IMAGE_URL)
                .placeholder(R.mipmap.timg)
                .into(iv_show);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mStartPoint = new Point((int)(mRandom.nextFloat() * (Math.random()>0.5?1:-1) * mScreenWidth),-200);
            mEndPoint = new Point((int)(mRandom.nextFloat() *(Math.random()>0.5?1:-1) * mScreenWidth),mScreenHeight + 50);
            mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
            mConTwoPoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
            addMyView();
            mHandler.postDelayed(this, 600);
        }
    };

    /**
     * 初始化
     */
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_star_img);
        mRandom = new Random();
    }

    /**
     * 添加view
     */
    private void addMyView(){
        mCopyBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mCopyBitmap);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        canvas.drawColor(mColors[mRandom.nextInt(mColors.length)], PorterDuff.Mode.SRC_IN);
        final ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(mCopyBitmap);


//        HeartView heartView = new HeartView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.addRule(CENTER_HORIZONTAL);
        backView.addView(imageView,params);
        initAnimation(imageView);
    }

    /**
     * 初始化动画
     *
     * @param imageView
     */
    private void initAnimation(final View imageView) {

        Point conOnePoint = this.mConOnePoint;
        Point conTwoPoint = this.mConTwoPoint;
        Point startPoint = this.mStartPoint;
        Point endPoint = this.mEndPoint;

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(conOnePoint, conTwoPoint), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                backView.removeView(imageView);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.2f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(6000);
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.start();

    }

    class MyTypeEvaluator implements TypeEvaluator<Point> {

        private Point onePoint;
        private Point twoPoint;

        public MyTypeEvaluator(Point onePoint, Point twoPoint) {
            this.onePoint = onePoint;
            this.twoPoint = twoPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            //利用三阶贝塞尔曲线公式算出中间点坐标
            return BezierUtil.CalculateBezierPointForCubicPoint(t, startValue, onePoint, twoPoint, endValue);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
