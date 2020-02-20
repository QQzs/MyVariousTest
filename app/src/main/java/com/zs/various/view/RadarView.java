package com.zs.various.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by zs
 * Date：2017年 05月 09日
 * Time：20:17
 * —————————————————————————————————————
 * About: 雷达扫描效果
 * —————————————————————————————————————
 */

public class RadarView extends View {

    private Paint mBgPaint;

    private Paint mCriclePaint;

    private int mRadius;

    private int mCricleRadius;

    private ValueAnimator[] mValueAnimators = new ValueAnimator[2];

    private ValueAnimator[] mValueAnimators2 = new ValueAnimator[4];

    private AnimatorSet mAnimatorSet;

    private AnimatorSet mAnimatorScaleSet;

    private int mColor = 0xFF4081;

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initAnimation();
        initScaleAnimSet();
        initAnimatorSet();

    }

    /**
     * 初始化画笔
     */
    private void initPaint(){

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(0x66ee4000);
        mBgPaint.setStyle(Paint.Style.FILL);

        mCriclePaint = new Paint();
        mCriclePaint.setAntiAlias(true);
        mCriclePaint.setColor(mColor);
        mCriclePaint.setStrokeWidth(3);
        mCriclePaint.setStyle(Paint.Style.STROKE);

    }

    /**
     * 初始化动画
     */
    private void initAnimation(){

        for(int i = 0 ; i < mValueAnimators.length;i++ ){
            mValueAnimators[i] = new ValueAnimator().ofFloat(0,1);
            mValueAnimators[i].setDuration(2000);
            mValueAnimators[i].setInterpolator(new DecelerateInterpolator());
            mValueAnimators[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mCricleRadius = (int) (value * mRadius);
                    if (value > 0.7){
                        mCriclePaint.setAlpha((int) (255 * (1-value)));
                    }else{
                        mCriclePaint.setAlpha(100);
                    }
                    mCriclePaint.setStrokeWidth(3 + value);
                    invalidate();
                }
            });
        }
    }

    private void initScaleAnimSet() {
        //沿x轴放大
        mValueAnimators2[0] = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.2f, 1f);
        //沿y轴放大
        mValueAnimators2[1] = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.2f, 1f);

        //沿x轴放大
        mValueAnimators2[2] = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.2f, 1f);
        //沿y轴放大
        mValueAnimators2[3] = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.2f, 1f);
        mAnimatorScaleSet = new AnimatorSet();

        //都设置2s，也可以为每个单独设置
        mAnimatorScaleSet.setDuration(2000);
        //同时沿X,Y轴放大
        mAnimatorScaleSet.play(mValueAnimators2[0]).with(mValueAnimators2[1]);
        mAnimatorScaleSet.play(mValueAnimators2[2]).with(mValueAnimators2[3]).after(mValueAnimators2[0]);
        mAnimatorScaleSet.start();

        mAnimatorScaleSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorScaleSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * 初始化动画组
     */
    private void initAnimatorSet(){
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(mValueAnimators[0]).before(mValueAnimators[1]);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (int) (Math.min(w,h) * 0.3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画圆背景
        canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mBgPaint);
        // 画圆环
        canvas.drawCircle(getWidth()/2,getHeight()/2,mCricleRadius,mCriclePaint);
    }

    public void startAnim(){
        if (!mAnimatorSet.isRunning()){
            Log.d("My_Anim","startAnim  111111111111");
            mAnimatorSet.start();
        }
        if (!mAnimatorScaleSet.isRunning()){
            Log.d("My_Anim","startAnim  22222222222");
            //同时沿X,Y轴放大
            mAnimatorScaleSet.start();
        }
    }

    public void stopAnim(){
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            Log.d("My_Anim","stopAnim 111");
            mAnimatorSet.cancel();
            clearAnimation();
        }
        if (mAnimatorScaleSet != null && mAnimatorScaleSet.isRunning()) {
            Log.d("My_Anim","stopAnim 222");
            mAnimatorScaleSet.cancel();
            clearAnimation();
        }
    }

}
