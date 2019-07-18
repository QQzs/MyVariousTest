package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zs.various.R;

/**
 * @Author: zs
 * @Date: 2019-07-15 16:59
 * @Description:
 */
public class ShadowLayout extends RelativeLayout {

    private float mWidth;
    private float mHeight;
    private Paint mPaint = new Paint();

    /**
     * 阴影的颜色
     */
    private int mShadowColor;

    /**
     * 阴影 x 轴的偏移量
     */
    private float mShadowDx;

    /**
     * 阴影 y 轴的偏移量
     */
    private float mShadowDy;

    /**
     * 阴影模糊半径
     */
    private float mShadowRadius;

    /**
     * 阴影高度
     */
    private float mShadowHeight;

    /**
     * 阴影的圆角弧度
     */
    private float mShadowLayoutRadius;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context , attrs , defStyleAttr);
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs , R.styleable.ShadowView);
        mShadowColor = typedArray.getColor(R.styleable.ShadowView_shadowColor , Color.GRAY);
        mShadowRadius = typedArray.getDimension(R.styleable.ShadowView_shadowRadius , dp2px(5));
        mShadowDx = typedArray.getDimension(R.styleable.ShadowView_shadowDx , 0);
        mShadowDy = typedArray.getDimension(R.styleable.ShadowView_shadowDy , dp2px(5));
        mShadowHeight = typedArray.getDimension(R.styleable.ShadowView_shadowHeight , dp2px(10));
        mShadowLayoutRadius = typedArray.getDimension(R.styleable.ShadowView_shadowLayoutRadius , dp2px(10));

        init();

    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = this.getMeasuredWidth();
        mHeight = this.getMeasuredHeight() + mShadowHeight;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize((int)mWidth), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize((int)mHeight), MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 设置阴影颜色
     * @param color
     */
    public void setShadowColor(int color){
        this.mShadowColor = color;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        RectF rect = new RectF(mWidth * 0.1f , 0, mWidth - mWidth * 0.1f, mHeight - mShadowHeight);
        canvas.drawRoundRect(rect, mShadowLayoutRadius, mShadowLayoutRadius, mPaint);

    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
