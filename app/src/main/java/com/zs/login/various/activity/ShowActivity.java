package com.zs.login.various.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zs.login.various.R;

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

    /**
     * handler
     **/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;

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

        // 计算隐藏布局的高度
        mDensity = getResources().getDisplayMetrics().density;
        mHiddenViewMeasuredHeight = (int) (mDensity * 120 + 0.5);
        Log.d("My_Height", "mHiddenViewMeasuredHeight = " + mHiddenViewMeasuredHeight);

//        animateClose(mHiddenLayout);
        alphaAnimator();

    }

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
        if (mIsShow) {
            mIsShow = false;
            animateClose(mHiddenLayout);
            animationIvClose();
        } else {
            mIsShow = true;
            animateOpen(mHiddenLayout);
            animationIvOpen();
        }
    }

    private void animationIvOpen() {
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(300);
        mIv.startAnimation(animation);
    }

    private void animationIvClose() {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(200);
        mIv.startAnimation(animation);
    }

    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();

    }

    private void animateClose(final View view) {
        ValueAnimator animator = createDropAnimator(view, mHiddenViewMeasuredHeight, 0);
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
        animator.setDuration(1200);
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
