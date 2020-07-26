package com.zs.various.view.flowlayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zs.various.view.flowlayout.adapter.BaseFlowTagAdapter;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/23 2:42 PM
 * @Description:
 */
public class FlowTagLayoutMine extends ViewGroup {

    private int mMaxLine = 2;
    private boolean mIsFold = false;
    private FoldViewListener mListener;
    private int foldPosition = -1;

    private int mTagMarginRight = 20;
    private int mTagMarginBottom = 10;
    private int foldViewWidth = 100;

    protected BaseFlowTagAdapter mTagAdapter;

    private AdapterDataObserver mDataObserver;

    public FlowTagLayoutMine(Context context) {
        this(context, null);
    }

    public FlowTagLayoutMine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTagLayoutMine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int rowsWidth = getPaddingLeft();
        int columnHeight = getPaddingTop();
        int childWidth = 0;
        int childHeight = 0;
        // 行数
        int line = 1;
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            if (foldPosition > 0 && i > foldPosition) {
                break;
            }
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 获取元素测量宽度和高度
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();

            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            childWidth = childMeasuredWidth + mTagMarginRight;
            childHeight = childMeasuredHeight + mTagMarginBottom;

            if (rowsWidth + childWidth > widthSize && i != 0) {
                // 该行已占大小+该元素大小>父容器宽度  则换行
                if (mMaxLine > 0 && line == mMaxLine) {
//                    if (mIsFold) {
//                        measureFoldView(child , heightMeasureSpec);
//                        foldPosition = i;
//                    }
                    break;
                }
                // 高度加一行
                columnHeight += childHeight;
                rowsWidth = childWidth;
                line++;
            } else {
                rowsWidth += childWidth;
            }
//            if (mIsFold && mMaxLine > 0 && rowsWidth + foldViewWidth > widthSize && line + 1 > mMaxLine) {
//                if (childWidth + foldViewWidth > widthSize) {
//                    if (i < childCount - 1) {
//                        reMeasureChild(child , widthMeasureSpec , heightMeasureSpec);
//                        View foldChild = getChildAt(i + 1);
//                        measureFoldView(foldChild , heightMeasureSpec);
//                        break;
//                    }
//                } else {
//                    measureFoldView(child , heightMeasureSpec);
//                    foldPosition = i;
//                    break;
//                }
//            }
        }

        //设置flow的宽高
        setMeasuredDimension(widthSize, line * childHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int widthSize = getMeasuredWidth();
        int rowsWidth = getPaddingLeft();
        int columnHeight = getPaddingTop();
        int childWidth = 0;
        int childHeight = 0;
        int childLeft = 0;
        int childTop = 0;
        // 行数
        int line = 1;

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            if (foldPosition > 0 && i > foldPosition) {
                break;
            }
            View child = getChildAt(i);
            //获取元素测量宽度和高度
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();

            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            childWidth = childMeasuredWidth + mTagMarginRight;
            childHeight = childMeasuredHeight + mTagMarginBottom;

            // 该行已占大小+该元素大小>父容器宽度  则换行
            if (i != 0 && rowsWidth + childWidth > widthSize) {
                if (mMaxLine > 0 && line == mMaxLine) {
//                    if (mIsFold) {
//                        child.layout(childLeft , childTop , childLeft + foldViewWidth , childTop + childHeight - mTagMarginBottom);
//                        if (mListener != null) {
//                            mListener.onConvertFoldView(i , child);
//                        }
//                        foldPosition = i - 1;
//                    }
                    break;
                }
                // 高度加一行
                columnHeight += childHeight;
                // 重新回到最左边
                childLeft = getPaddingLeft();
                childTop = columnHeight;
                rowsWidth = childWidth;
                line ++;
            } else {
                childLeft = rowsWidth;
                childTop = columnHeight;
                // 宽度增加一个子View宽度
                rowsWidth += childWidth;

            }
//            if (mIsFold && mMaxLine > 0 && rowsWidth + foldViewWidth > widthSize && line + 1 > mMaxLine) {
//                if (childWidth + foldViewWidth > widthSize) {
//                    if (i < childCount - 1) {
//                        child.layout(childLeft , childTop , widthSize - foldViewWidth , childTop + childHeight - mTagMarginBottom);
//                        View foldChild = getChildAt(i + 1);
//                        foldChild.layout(rowsWidth - foldViewWidth , childTop , rowsWidth - mTagMarginRight , childTop + childHeight - mTagMarginBottom);
//                        if (mListener != null) {
//                            mListener.onConvertFoldView(i + 1 , child);
//                        }
//                        foldPosition = i + 1;
//                        break;
//                    }
//                } else {
//                    child.layout(childLeft , childTop , childLeft + foldViewWidth , childTop + childHeight - mTagMarginBottom);
//                    if (mListener != null) {
//                        mListener.onConvertFoldView(i , child);
//                    }
//                    foldPosition = i;
//                    break;
//                }
//            }
            child.layout(childLeft, childTop, childLeft + childWidth - mTagMarginRight, childTop + childHeight - mTagMarginBottom);
        }
    }

    private void reMeasureChild(View child, int widthMeasureSpec, int heightMeasureSpec) {
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) - foldViewWidth
                , MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec)
                , heightMeasureSpec);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private void measureFoldView(View child, int heightMeasureSpec) {
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(foldViewWidth
                , MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec)
                , heightMeasureSpec);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 设置tag适配器
     * @param adapter       数据
     */
    public void setTagAdapter(BaseFlowTagAdapter adapter) {
        initAdapter(adapter);
    }

    /**
     * 初始化tag适配器
     */
    private void initAdapter(BaseFlowTagAdapter adapter) {
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
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
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
    }

    public void setFoldListener(FoldViewListener listener) {
        this.mListener = listener;
    }

    public interface FoldViewListener {
        void onConvertFoldView(int position, View foldView);
    }

}
