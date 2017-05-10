package com.zs.login.myvarioustest.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by zs
 * Date：2017年 05月 08日
 * Time：15:11
 * —————————————————————————————————————
 * About: 动画Button
 * —————————————————————————————————————
 */

public class AnimButton extends View {

    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;
    /**
     * 圆角半径
     */
    private int circleAngle;
    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;
    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;
    /**
     * 按钮文字字符串
     */
    private String buttonString = "确认完成";
    /**
     * 动画执行时间
     */
    private int duration = 2000;
    /**
     * view向上移动距离
     */
    private int move_distance = 300;

    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;
    /**
     * 文字绘制所在矩形
     */
    private Rect textRect = new Rect();

    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    /**
     * 矩形到圆角矩形过度的动画
     */
    private ValueAnimator animator_rect_to_angle;
    /**
     * 矩形到正方形过度的动画
     */
    private ValueAnimator animator_rect_to_square;
    /**
     * view上移的动画
     */
    private ObjectAnimator animator_move_to_up;
    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator animator_draw_ok;

    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk = false;

    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectf = new RectF();

    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();
    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect effect;
    /**
     * 点击事件及动画事件2完成回调
     */
    private AnimationButtonListener animationButtonListener;

    public AnimButton(Context context) {
        this(context,null);
    }

    public AnimButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationButtonListener != null) {
                    animationButtonListener.onClickListener();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationButtonListener != null) {
                    animationButtonListener.animationFinish();
                }
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
        width = w;
        height = h;

        default_two_circle_distance = (w - h) / 2;
        initAnimation();
        initOkPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw_oval_to_circle(canvas);
        drawText(canvas);

        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }
    }

    /**
     * 初始化Paint
     */
    private void initPaint(){

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bg_color);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);

        okPaint = new Paint();
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setStrokeWidth(10);
        okPaint.setColor(Color.WHITE);
        okPaint.setAntiAlias(true);

    }

    /**
     * 初始化动画
     */
    private void initAnimation(){
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        set_move_to_up_animation();
        set_draw_ok_animation();
//        animatorSet.play(animator_move_to_up).before(animator_draw_ok).after(animator_rect_to_square).after(animator_rect_to_angle);

        animatorSet.play(animator_rect_to_square).with(animator_rect_to_angle);
        animatorSet.play(animator_move_to_up).after(animator_rect_to_square);
        animatorSet.play(animator_draw_ok).after(animator_move_to_up);

    }

    /**
     * 画圆角矩形
     * @param canvas
     */
    private void draw_oval_to_circle(Canvas canvas){

        rectf.left = two_circle_distance;
        rectf.top = 0;
        rectf.right = width - two_circle_distance;
        rectf.bottom = height;

        canvas.drawRoundRect(rectf,circleAngle,circleAngle,paint);

    }

    /**
     * 设置矩形过度到圆角矩形的动画
     */
    private void set_rect_to_angle_animation(){
        animator_rect_to_angle = ValueAnimator.ofInt(0,height/2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    /**
     * 设置圆角矩形过度到圆的动画
     */
    private void set_rect_to_circle_animation(){

        animator_rect_to_square = ValueAnimator.ofInt(0,default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                two_circle_distance = (int) animation.getAnimatedValue();
                int alpha = 255 - two_circle_distance * 255 / default_two_circle_distance;
                textPaint.setAlpha(alpha);
                invalidate();
            }
        });

    }

    /**
     * 设置view上移的动画
     */
    private void set_move_to_up_animation(){
        final float curTranslationY = this.getTranslationY();
        animator_move_to_up = ObjectAnimator.ofFloat(this,"translationY",curTranslationY,curTranslationY - move_distance);
        animator_move_to_up.setDuration(duration);
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    /**
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
    }


    /**
     * 初始化对勾的路径
     */
    private void initOkPath(){
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);
        pathMeasure = new PathMeasure(path, true);
    }


    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        textRect.left = 0;
        textRect.top = 0;
        textRect.right = width;
        textRect.bottom = height;

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int baseLine = (textRect.top + textRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2 ;
        canvas.drawText(buttonString,textRect.centerX(),baseLine,textPaint);
    }

    /**
     * 开启动画
     */
    public void startAnim(){
        animatorSet.start();
    }

    public interface AnimationButtonListener{

        void onClickListener();

        void animationFinish();

    }

    public void setAnimationButtonListener(AnimationButtonListener listener){
        this.animationButtonListener = listener;
    }


}
