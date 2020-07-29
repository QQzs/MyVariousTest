package com.zs.various.view.flowlayout;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zs.various.view.flowlayout.adapter.BaseFoldFlowAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by xuxiaobo on 2018/1/25.
 * 标签流布局
 * 备注 ：此控件，默认有左边距和上边距，数值等于 mTagMarginLeft 和 mTagMarginTop；
 */

public class FlowTagLayout2 extends ViewGroup {

    private static boolean sUseZeroUnspecifiedMeasureSpec;

    //标签右边距/下边距/最大行数
    private int mTagMarginLeft = 20;

    private int mTagMarginTop = 10;

    protected BaseFoldFlowAdapter mTagAdapter;

    private AdapterDataObserver mDataObserver;

    private FoldViewListener mListener;

    //标签流标签添加完成回调(loading可以取消)
    private FlowTagAddFinishedObservable mFlowTagAddFinishedObservable;

    /**
     * 是否需要展示折叠Tag（不能与isReverseOrder和Gravity同用）
     */
    private boolean isNeedFold = true;
    /**
     * 折叠的行数
     */
    private int foldLine = 2;

    /**
     * 存储一行宽度
     */
    private List<Integer> lineWidthList = new ArrayList<>();

    public FlowTagLayout2(Context context) {
        this(context, null);
        final int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        sUseZeroUnspecifiedMeasureSpec = targetSdkVersion < M;
    }

    public FlowTagLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTagLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowTagLayout);
//        mTagMarginLeft = typedArray.getDimensionPixelSize(R.styleable.FlowTagLayout_tagMarginRight, CommonUtils.dip2px(context, 8));
//        mTagMarginTop = typedArray.getDimensionPixelSize(R.styleable.FlowTagLayout_tagMarginBottom, CommonUtils.dip2px(context, 5));
//        mTagMaxLines = typedArray.getInteger(R.styleable.FlowTagLayout_maxLines, 0);
//        foldLine = typedArray.getInteger(R.styleable.FlowTagLayout_foldLine, 0);
//        isNeedFold = typedArray.getBoolean(R.styleable.FlowTagLayout_isNeedFold,false);
//        isShowMore = typedArray.getBoolean(R.styleable.FlowTagLayout_isShowMore,false);
//        isReverseOrder = typedArray.getBoolean(R.styleable.FlowTagLayout_isReverseOrder,false);
//        maxChildCountOfLine = typedArray.getInteger(R.styleable.FlowTagLayout_maxChildCountOfLine,0);
//        typedArray.recycle();
    }

    /**
     * 更新折叠状态
     * @param fold
     */
    public void setNeedFold(boolean fold) {
        this.isNeedFold = fold;
        reloadData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;
        //测量时每一行的宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;
        //标签行数
        int line = 0;

        lineWidthList.clear();
        //遍历每个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            int realChildWidth = childWidth + mTagMarginLeft;
            int realChildHeight = childHeight + mTagMarginTop;

            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            //如果第一个view的宽度就超过了父布局的宽度则不换行
            if ((lineWidth + realChildWidth) > sizeWidth && i != 0) {
                //行数大于最大行数结束绘制
                line++;
                // 行数大于折叠行数结束绘制
                if (isNeedFold && foldLine > 0 && line >= foldLine) {
                    resultHeight += (lineHeight + mTagMarginTop);
                    View moreView = getChildAt(getChildCount() - 1);
                    if (moreView != null) {
                        measureChild(moreView , widthMeasureSpec , heightMeasureSpec );
                    }
                    break;
                }
                //记录一行的总宽度
                lineWidthList.add(lineWidth);
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行
            if (i == childCount - 1) {
                lineWidthList.add(lineWidth);
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += (lineHeight + mTagMarginTop);
            }

        }

        if (isNeedFold) {
            //需要展示查看更多，先测量
            View moreView = getChildAt(getChildCount() - 1);
            if (moreView != null) {
                measureChild(moreView,widthMeasureSpec, heightMeasureSpec);
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int flowWidth = getMeasuredWidth();
        int flowHeight = getMeasuredHeight();
        int childLeft = 0;
        int childTop = 0;
        //标签行数(从0开始)
        int line = 0;
        int childCount = getChildCount();
        //遍历子控件，记录每个子view的位置
        for (int i = 0 ; i < childCount; i++) {
            View childView = getChildAt(i);
            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //用来记录最后一行的偏移量
            int widthOffset = 0;

            // 用来记录最后一行的偏移量
            if (isNeedFold && foldLine > 0 && line + 1 == foldLine) {
                //如果展示折叠view，需要提前将空间预留出来
                widthOffset = getChildAt(getChildCount() - 1).getMeasuredWidth() + mTagMarginLeft;
            }

            if (childLeft + mTagMarginLeft + childWidth > (flowWidth - widthOffset) && i != 0) {
                // 行数大于最大行数结束绘制
                line ++;
                // 需要折叠结束绘制
                if (isNeedFold && foldLine > 0 && line >= foldLine) {
                    //将折叠的View补防到预留位置
                    View foldView = getChildAt(getChildCount() - 1);
                    if (foldView != null) {
                        layoutMoreViewInReservedPosition(foldView,childLeft,childTop);
                        mTagAdapter.onConvertFoldView(foldView);
                        if (mListener != null) {
                            mListener.onConvertFoldView(i , foldView);
                        }
                    }
                    break;
                }

                //换行处理
                childTop += (mTagMarginTop + childHeight);
                childLeft = 0;
            }

            //布局
            int left = childLeft + mTagMarginLeft;
            int top = childTop + mTagMarginTop;
            int right = childLeft + childWidth + mTagMarginLeft;
            int bottom = childTop + childHeight + mTagMarginTop;

            childView.layout(left, top, right, bottom);
            childLeft += (childWidth + mTagMarginLeft);

        }
    }

    /**
     * 将查看更多的View补防到预留位置
     * @param moreView
     * @param childLeft
     * @param childTop
     */
    private void layoutMoreViewInReservedPosition(View moreView, int childLeft, int childTop) {
        int left = childLeft + mTagMarginLeft;
        int top = childTop + mTagMarginTop;
        int right = childLeft + moreView.getMeasuredWidth() + mTagMarginLeft;
        int bottom = childTop + moreView.getMeasuredHeight() + mTagMarginTop;
        moreView.layout(left, top, right, bottom);
    }


    /**
     * 设置TagView
     *
     * @param list
     */
    public void setTagView(List<View> list) {
        removeAllViews();
        for (int i = 0 , count = list.size(); i < count; i++) {
            addView(list.get(i), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width, mTagMarginLeft);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingTop() + getPaddingBottom(), lp.height,0);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 重写了View Group的测量子view的方法 添加了一个修正值
     * @param spec 父view测量数据
     * @param padding padding
     * @param childDimension dim
     * @param correct 因为自定义控件有个自定义的TagMargin,当一排只有一个的时候需要给子view减去一个这个值防止子view超出父布局宽度
     * @return
     */
    public static int getChildMeasureSpec(int spec, int padding, int childDimension,int correct) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);

        int size = Math.max(0, specSize - padding);

        int resultSize = 0;
        int resultMode = 0;

        switch (specMode) {
            // Parent has imposed an exact size on us
            case MeasureSpec.EXACTLY:
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    // Child wants to be our size. So be it.
                    resultSize = size;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    // Child wants to determine its own size. It can't be
                    // bigger than us.
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;

            // Parent has imposed a maximum size on us
            case MeasureSpec.AT_MOST:
                if (childDimension >= 0) {
                    // Child wants a specific size... so be it
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    // Child wants to be our size, but our size is not fixed.
                    // Constrain child to not be bigger than us.
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    // Child wants to determine its own size. It can't be
                    // bigger than us.
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;

            // Parent asked to see how big we want to be
            case MeasureSpec.UNSPECIFIED:
                if (childDimension >= 0) {
                    // Child wants a specific size... let him have it
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    // Child wants to be our size... find out how big it should
                    // be
                    resultSize = sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    // Child wants to determine its own size.... find out how
                    // big it should be
                    resultSize = sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                }
                break;
            default:
                break;
        }
        if (resultSize>correct){
            resultSize = resultSize - correct;
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
    }

    /**
     * 设置tag适配器
     * @param adapter       数据
     */
    public void setTagAdapter(BaseFoldFlowAdapter adapter) {
        initAdapter(adapter);
    }

    /**
     * 初始化tag适配器
     */
    private void initAdapter(BaseFoldFlowAdapter adapter) {
        if (mDataObserver != null && mTagAdapter != null) {
            mTagAdapter.unregisterDataSetObserver(mDataObserver);
            mTagAdapter.onDetachedFromParent(this);
        }

        removeAllViews();
        mTagAdapter = adapter;
        if (mTagAdapter != null) {
            mDataObserver = new AdapterDataObserver();
            mTagAdapter.registerDataSetObserver(mDataObserver);
            adapter.onAttachedToParent(this);
        }
    }

    /**
     * 适配器数据变化观察者
     */
    private class AdapterDataObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            //适配器数据发生变化，重新加载布局
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    //view添加结束监听
    public interface FlowTagAddFinishedObservable {
        void addFinished();
    }

    /**
     * 数据变化重新加载布局
     */
    protected void reloadData() {
        removeAllViews();
        final int count = mTagAdapter.getCount();
        for (int i = 0; i < count; i++) {
            final View childView = mTagAdapter.getView(i, null, this);
            addView(childView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        if (mFlowTagAddFinishedObservable != null) {
            mFlowTagAddFinishedObservable.addFinished();
        }
    }

    public void setFoldListener(FoldViewListener listener) {
        this.mListener = listener;
    }

    public interface FoldViewListener {
        void onConvertFoldView(int position, View foldView);
    }
}