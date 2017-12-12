package com.zs.various.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zs.various.R;
import com.zs.various.util.DensityUtil;

/**
 * Created by zs
 * Date：2017年 06月 06日
 * Time：18:29
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ShowActivity extends Activity {

    private LinearLayout mHiddenLayout;

    private float mDensity;

    private int mHiddenViewMeasuredHeight;

    private ImageView mIv;

    private boolean mIsShow = false;
    private boolean mIsAnim = false;

    private View view_bottom,view_0,view_1, view_2;
    private int mViewHeight;
    private int mViewHeight1;
    private int mViewHeight2;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_layout);
        mHiddenLayout = (LinearLayout) findViewById(R.id.linear_hidden);
        mIv = (ImageView) findViewById(R.id.my_iv);
        view_bottom = findViewById(R.id.view_bottom);
        view_0 = findViewById(R.id.view_0);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);


        // 计算隐藏布局的高度
        mHiddenViewMeasuredHeight = DensityUtil.dip2px(this,120);
        mViewHeight1 = DensityUtil.dip2px(this,150);
        mViewHeight2 = DensityUtil.dip2px(this,200);
        mViewHeight = mViewHeight2 - mViewHeight1;

//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        view_1.measure(w, h);
//        Log.d("My_Height", "h2  ===== " + view_1.getMeasuredHeight());

//        view_1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                view_1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                Log.d("My_Height"," h ==== "+ view_1.getMeasuredHeight());
//            }
//        });

//        animateClose(mHiddenLayout);
//        alphaAnimator();

    }

    /**
     * view 透明度动画
     */
    private void alphaAnimator() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mHiddenLayout, "alpha", 1f, 0f);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
//                AnimatorSet set = new AnimatorSet();
//                set.play(objectAnimator);
//                set.setDuration(1000);
//                set.start();

//                ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.0f);
//                animator.setDuration(1200);
//                animator.setInterpolator(new AccelerateInterpolator());
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator arg0) {
//                        float value = (float) arg0.getAnimatedValue();
//                        mHiddenLayout.setAlpha(value);
//                    }
//                });
//                animator.start();
            }
        }, 2000);


    }

    public void onClick(View v) {
        // 上半部分动画
        if (mIsShow) {
            animationIvClose();
            animateClose(mHiddenLayout,mHiddenViewMeasuredHeight);
        } else {
            animationIvOpen();
            animateOpen(mHiddenLayout,mHiddenViewMeasuredHeight);
        }

        // 下半部分动画
        if (mIsShow) {
            mIsShow = false;
            testAnimDown(view_bottom,mViewHeight,500);
            testAnimUp(view_1,-mViewHeight1,400);
            testAnimDown(view_2,mViewHeight2,600);
        } else {
            mIsShow = true;
            testAnimUp(view_bottom,mViewHeight,600);
            testAnimDown(view_1,-mViewHeight1,500);
            testAnimUp(view_2,mViewHeight2,400);
        }
    }


    private void testAnimUp(final View view , int height , long time){
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, height,0);
        objectAnimator.setDuration(time);
        objectAnimator.start();
    }

    private void testAnimDown(final View view , int height , long time){
        view.clearAnimation();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, height);
        objectAnimator.start();
        objectAnimator.setDuration(time);
    }

    /**
     * view打开 图标旋转动画
     */
    private void animationIvOpen() {
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(300);
        mIv.startAnimation(animation);
    }

    /**
     * view关闭 图标旋转动画
     */
    private void animationIvClose() {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(200);
        mIv.startAnimation(animation);
    }

    private void animateOpen(View view , int height) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0,
                height);
        animator.start();
    }

    private void animateClose(final View view , int height) {
        ValueAnimator animator = createDropAnimator(view, height, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                view.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
