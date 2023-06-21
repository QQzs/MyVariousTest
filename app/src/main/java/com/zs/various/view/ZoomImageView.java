package com.zs.various.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.zs.various.util.DensityUtil;
import com.zs.various.util.ScreenUtil;


public class ZoomImageView extends ImageView implements OnScaleGestureListener,
        OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener

{
    /**
     * 允许放大的最大级别
     */
    public static float SCALE_MAX = 4.0f;
    /**
     * 双击缩放时,实现平滑的缩放时,每一次缩放任务的时间间隔
     */
    private static final int DELAY_TIME = 5;
    private final int mTouchSlop;

    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float mInitScale = 1.0f;
    /**
     * 当前图片的缩放值
     */
    private float mCurrentScale = 1.0f;
    /**
     * true表示需要重新初始化图片位置
     */
    private boolean mInitContent;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] mMatrixValues = new float[9];

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    /**
     * 存放图片数据的矩阵
     */
    private final Matrix mScaleMatrix = new Matrix();

    /**
     * 上一次手指的位置
     */
    private float mLastX;
    private float mLastY;

    /**
     * 上次的触摸点个数
     */
    private int mLastPointerCount;

    /**
     * 默认居中显示,如果宽度大于长度，半居中模式，上下1:2，左右居中,true需要重新定义中心
     */
    private boolean mNewCenter;
    /**
     * 在原来中心的基础的偏移量
     */
    private int mNewDeltaY;
    /**
     * 双击的手势检测
     */
    private GestureDetector mDoubleClickDetector;
    /**
     * 当前正在执行双击缩放
     */
    private boolean isAutoScale;

    /**
     * 是否支持缩放
     */
    private boolean isSupportZoom = false;
    /**
     * 图片上的删除按钮
     */
    private ImageView mSwithBtn;

    /**
     * 是否是拖拽行为
     */
    private boolean mIsDragging;

    /**
     * 是否添加删除按钮
     */
    private boolean isAddSwitchBtn;

    private boolean isFixCenter;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        setBackgroundColor(Color.parseColor("#2B2727"));
        final ViewConfiguration configuration = ViewConfiguration
                .get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        initGestureDetector();
    }

    /**
     * 初始化手势检测
     */
    private void initGestureDetector() {
        mDoubleClickDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (isAutoScale == true) {
                            return true;
                        }
                        float x = e.getX();
                        float y = e.getY();
                        isAutoScale = true;
                        if (getScale() < SCALE_MAX) {
                            postDelayed(
                                    new AutoScaleRunnable(SCALE_MAX, x, y), DELAY_TIME);
                        } else {
                            postDelayed(
                                    new AutoScaleRunnable(mInitScale, x, y), DELAY_TIME);
                        }
                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (hasOnClickListeners()) {
                            performClick();
                            return true;
                        }
                        return super.onSingleTapConfirmed(e);
                    }
                });
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    /**
     * 强制居中
     *
     * @param fixCenter
     */
    public void setFixCenter(boolean fixCenter) {
        isFixCenter = fixCenter;
    }


    /**
     * 自动缩放的任务
     */
    private class AutoScaleRunnable implements Runnable {
        /**
         * 放大操作临界值
         */
        static final float BIGGER = 1.07f;
        /**
         * 缩小操作临界值
         */
        static final float SMALLER = 0.93f;
        /**
         * 本次任务的目标值
         */
        private float mTargetScale;
        /**
         * 本次任务的中间值
         */
        private float mTempScale;
        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         *
         * @param targetScale
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                mTempScale = BIGGER;
            } else {
                mTempScale = SMALLER;
            }
        }

        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(mTempScale, mTempScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            final float currentScale = getScale();
            // 如果值在合法范围内，继续缩放
            if (((mTempScale > 1f) && (currentScale < mTargetScale))
                    || ((mTempScale < 1f) && (mTargetScale < currentScale))) {
                //通过延迟任务实现平滑的放大缩小操作
                postDelayed(this, DELAY_TIME);
            } else
            // 其它情况直接缩放到目标值
            {

                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
                mCurrentScale = mTargetScale;
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();//当前放大的倍数
        mCurrentScale = scale;//记录当前的放大倍数
        float scaleFactor = detector.getScaleFactor();//当前手势处于的状态,scaleFactor > 1.0f为放大动作,scaleFactor < 1.0f缩小动作
        if (getDrawable() == null) {
            return true;
        }
        /**
         * 缩放的范围控制
         */
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > mInitScale && scaleFactor < 1.0f)) {
            //最小值判断
            if (scaleFactor * scale < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            //最大值判断
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;//4.0f/2.0f
            }

            //设置缩放比例
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            //边界检测
            checkBorderAndCenterWhenScale();
            //刷新界面
            setImageMatrix(mScaleMatrix);
        }
        return true;

    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        if (mNewCenter) {
            deltaY += mNewDeltaY;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isSupportZoom) {
            //不支持缩放,不做任何处理
            return false;
        }
        if (mDoubleClickDetector.onTouchEvent(event)) {
            //双击检测
            return true;
        }
        //缩放检测
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        //每当触摸点发生变化时，重置mLasX , mLastY
        if (pointerCount != mLastPointerCount) {
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    //处于放大级别,父控件不处理事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    //处于放大级别,父控件不处理事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (mCurrentScale == mInitScale) {
                    //修正一下,如果当前缩放级别和初始化的缩放级别相等时,事件交由父控件处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                //移动的变化量
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!mIsDragging) {
                    mIsDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                }

                if (mIsDragging) {
                    if (getDrawable() != null && (dx != 0 || dy != 0)) {
                        //平移,平移的是距离,即变化量
                        mScaleMatrix.postTranslate(dx, dy);
                        RectF rect = getMatrixRectF();
                        //边界检测
                        if ((dx > 0 && rect.left > 0) || (dx < 0 && rect.right < getWidth())) {
                            mScaleMatrix.postTranslate(-dx, 0);
                        }
                        if ((dy > 0 && rect.top > 0) || (dy < 0 && rect.bottom < getHeight())) {
                            mScaleMatrix.postTranslate(0, -dy);
                        }
                        //刷新界面
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (mInitContent) {
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            //控件的宽高
            int viewWidth = ScreenUtil.getScreenWidth();
            int viewHeight = ScreenUtil.getScreenHeight() - DensityUtil.getStatusBarHeight(getContext());
            // 图片的宽和高
            int drawableWidth = d.getIntrinsicWidth();
            int drawableHeight = d.getIntrinsicHeight();
            if (drawableWidth == 0 || drawableHeight == 0 || viewHeight == 0 || viewWidth == 0) {
                return;
            }
            float scale;
            if (drawableWidth < drawableHeight) {
                //图片的宽小于图片的高
                scale = viewWidth * 1.0f / drawableWidth;
                int maxHeight = (int) (drawableHeight * scale);
                if (maxHeight > viewHeight) {
                    //拉升高度超过屏幕高，说明不能横向占满全屏，此时应该高度拉升到全屏，宽度居中
                    scale = viewHeight * 1.0f / drawableHeight;

                } else {
                    //拉升高度不超过屏幕高，说明可以上下居中
                }

            } else {
                //如果宽度大于长度，半居中模式，上下1:2，左右居中
                //全屏逻辑
                mNewCenter = true;
                if (viewWidth / viewHeight > drawableWidth / drawableHeight) {
                    //长金箍棒图
                    scale = viewHeight * 1.0f / drawableHeight;

                } else {
                    //宽金箍棒图
                    scale = viewWidth * 1.0f / drawableWidth;
                }
            }

            mInitScale = scale;
            SCALE_MAX = mInitScale * 4;
            mCurrentScale = scale;

            mScaleMatrix.preTranslate((viewWidth - drawableWidth) / 2, (viewHeight - drawableHeight) / 2);
            mScaleMatrix.postScale(scale, scale, viewWidth / 2,
                    viewHeight / 2);
            if (mNewCenter && !isFixCenter) {
                //上下1:2，左右居中
                mNewDeltaY = -(int) ((viewHeight - drawableHeight * scale) / 6);
                mScaleMatrix.postTranslate(0, mNewDeltaY);
            }
            // 图片移动至屏幕中心
            setImageMatrix(mScaleMatrix);
            mInitContent = false;
            if (isAddSwitchBtn) {
                //传入放大后图片的高度,用于定位删除按钮的位置
                addSwitchBtn(drawableHeight * scale);
            }
        }

    }

    /**
     * 添加切换图片模式的按钮
     */
    private void addSwitchBtn(float drawableHeight) {
//        RelativeLayout parent = (RelativeLayout) getParent();
//        mSwithBtn = new ImageView(getContext());
//        mSwithBtn.setClickable(true);
//        mSwithBtn.setImageResource(R.drawable.icon_bro_connect_close_picture_n);
//        int size = getContext().getResources().getDimensionPixelOffset(R.dimen.switch_btn_size);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, size);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        params.topMargin = getHeight() / 5/*(int) (getHeight() / 2 + mNewDeltaY - size / 2 )*/;
//        parent.addView(mSwithBtn, params);
//        mSwithBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    /**
     * 客态下,重新设置图片时,需要初始化一下图片大小及位置
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        refresh();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        refresh();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        refresh();
    }

    /**
     * 重置矩阵状态，防止旋转屏幕后造成的图片大小前后不一致
     */
    public void refresh() {
        mInitContent = true;
        mScaleMatrix.reset();
        postInvalidate();
    }

    /**
     * 图片回到初始化位置(从清屏模式回到非清屏模式需要将所有图片重置到最初状态)
     */
    public void resetBitmapLocation() {
        isAutoScale = true;
        postDelayed(
                new AutoScaleRunnable(mInitScale, getWidth() / 2,
                        getHeight() / 2), DELAY_TIME);
    }


    /**
     * 支持缩放
     *
     * @param supportZoom
     */
    public void setSupportZoom(boolean supportZoom) {
        isSupportZoom = supportZoom;
    }

    public void setAddSwitchBtn(boolean addSwitchBtn) {
        isAddSwitchBtn = addSwitchBtn;
    }


    //生成圆角图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 10;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

//    /*四个角的xy半径，左上,右上，右下，左下*/
//    private float[] rids = {15.0f,15.0f,15.0f,15.0f,15.0f,15.0f,15.0f,15.0f};
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Path path = new Path();
//        path.addRoundRect(new RectF(0,0,getWidth(),getHeight()),rids,Path.Direction.CW);
//        canvas.clipPath(path);
//        super.onDraw(canvas);
//    }

    /**
     * 是否处于缩放状态
     *
     * @return true:缩放状态，false：未处于缩放状态
     */
    public boolean isScale() {
        return mInitScale != getScale();
    }
}
