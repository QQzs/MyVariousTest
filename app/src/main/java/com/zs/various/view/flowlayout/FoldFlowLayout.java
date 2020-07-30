package com.zs.various.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zs.various.R;
import com.zs.various.view.flowlayout.adapter.BaseFoldFlowAdapter;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/23 2:42 PM
 * @Description:
 */
public class FoldFlowLayout extends ViewGroup {

    /**
     * 折叠的最大行数
     */
    private int mFoldLine;

    /**
     * 是否需要折叠
     */
    private boolean mNeedFold;

    /**
     * Tag 右边距
     */
    private int mTagMarginRight;
    /**
     * Tag 下边距
     */
    private int mTagMarginBottom;
    /**
     * 折叠view宽度
     */
    private int mFoldViewWidth;

    private int mRealViewHeight;

    /**
     * 数据适配器
     */
    protected BaseFoldFlowAdapter mTagAdapter;
    private AdapterDataObserver mDataObserver;

    public FoldFlowLayout(Context context) {
        this(context, null);
    }

    public FoldFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoldFlowLayout);
        mTagMarginRight = typedArray.getDimensionPixelSize(R.styleable.FoldFlowLayout_tagMarginRight, 20);
        mTagMarginBottom = typedArray.getDimensionPixelSize(R.styleable.FoldFlowLayout_tagMarginBottom, 10);
        mFoldViewWidth = typedArray.getDimensionPixelSize(R.styleable.FoldFlowLayout_foldViewWidth, 100);
        mFoldLine = typedArray.getInteger(R.styleable.FoldFlowLayout_foldLine, 0);
        mNeedFold = typedArray.getBoolean(R.styleable.FoldFlowLayout_isNeedFold,false);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int childWidth = 0;
        int childHeight = 0;
        int rowsWidth = getPaddingLeft();
        // 行数
        int line = 1;
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 获取元素测量宽度和高度
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();
            childWidth = childMeasuredWidth + mTagMarginRight;
            childHeight = childMeasuredHeight + mTagMarginBottom;

            if (rowsWidth + childWidth > widthSize && i != 0) {
                // 该行已占大小+该元素大小>父容器宽度  则换行
                if (mNeedFold && mFoldLine > 0) {
                    // 最后一个View 不测量 是收起View
                    if (i == childCount - 1) {
                       break;
                    }
                    // 测量下一个View，作为折叠View
                    if (line == mFoldLine && i < childCount - 1) {
                        View foldView = getChildAt(i + 1);
                        if (foldView != null) {
                            measureChild(foldView, widthMeasureSpec, heightMeasureSpec);
                        }
                        break;
                    }
                }
                rowsWidth = childWidth;
                line++;
            } else {
                rowsWidth += childWidth;
            }
        }
        //设置flow的宽高
        setMeasuredDimension(widthSize, line * childHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 控件本身宽度
        int widthSize = getMeasuredWidth();
        // 一行子view已经占得行总宽度
        int rowsWidth = getPaddingLeft();
        // view 已经占到列总高度
        int columnHeight = getPaddingTop();
        // 子view宽高
        int childWidth = 0;
        int childHeight = 0;
        // 子view绘制的左上角位置
        int childLeft = 0;
        int childTop = 0;
        // 行数
        int line = 1;
        // 展开按钮的宽度
        int widthOffset = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View child = getChildAt(i);
            // 获取元素测量宽度和高度 child.getMeasuredWidth+MarginRight
            // 注意此时不能child.getWidth,因为界面没有绘制完成，此时width为0
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();

            // child 实际占得宽高加上间隔
            childWidth = childMeasuredWidth + mTagMarginRight;
            childHeight = childMeasuredHeight + mTagMarginBottom;

            // 如果在折叠行需要设置widthOffset
            if (mNeedFold && line == mFoldLine) {
                widthOffset = mFoldViewWidth;
            }
            // 如果在折叠行的上一行，判断单个子View也可能会导致换行，也需要设置widthOffset
            if (mNeedFold && line == mFoldLine - 1 && rowsWidth + mFoldViewWidth > widthSize) {
                widthOffset = mFoldViewWidth;
            }
            // 该行已占大小+该元素大小>父容器宽度  则换行
            if (i != 0 && rowsWidth + childWidth + widthOffset > widthSize) {
                if (mNeedFold && mFoldLine > 0) {
                    if (line >= mFoldLine) {
                        // 超过折叠行数
                        childLeft = rowsWidth;
                        child.layout(childLeft, childTop, childLeft + mFoldViewWidth, childTop + childHeight - mTagMarginBottom);
                        // 当前view 不显示但是占据这个位置，防止重新layout
                        child.setVisibility(View.INVISIBLE);
                        if (i < childCount - 1) {
                            // 不是最后一个View，取下一个View作为折叠View
                            View foldView = getChildAt(i + 1);
                            foldView.layout(childLeft, childTop, childLeft + mFoldViewWidth, childTop + childHeight - mTagMarginBottom);
                            convertFoldView(foldView);
                        }
                        // 把最后一个view显示折叠 放在这个位置
//                        View foldView = getChildAt(childCount - 1);
//                        // 最后一个view可能就是要折叠的view ，需要再显示出来
//                        foldView.setVisibility(View.VISIBLE);
//                        foldView.layout(childLeft, childTop, childLeft + mFoldViewWidth, childTop + childHeight - mTagMarginBottom);
//                        convertFoldView(foldView);
                        break;
                    } else if (childWidth + mFoldViewWidth > widthSize && line + 1 >= mFoldLine) {
                        // 折叠行的上一行，并且这一个子view会导致换行，后面还有view
                        if (i < childCount - 1) {
                            childLeft = getPaddingLeft();
                            childTop = columnHeight + childHeight;
                            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight - mTagMarginBottom);
                            // 当前view 不显示但是占据这个位置，防止重新layout
                            child.setVisibility(View.INVISIBLE);
                            // 不是最后一个View，取下一个View作为折叠View
                            View foldView = getChildAt(i + 1);
                            foldView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight - mTagMarginBottom);
                            convertFoldView(foldView);
                            break;
                        }
                    }
                }
                // 高度加一行
                columnHeight += childHeight;
                // 重新回到最左边
                childLeft = getPaddingLeft();
                childTop = columnHeight;
                rowsWidth = childWidth;
                line++;
            } else {
                childLeft = rowsWidth;
                childTop = columnHeight;
                // 宽度增加一个子View宽度
                rowsWidth += childWidth;
            }
//            child.layout(childLeft, childTop, childLeft + childWidth - mTagMarginRight,
//                    childTop + childHeight - mTagMarginBottom);

            if (i == childCount -1) {
                // 如果超过折叠行数最后一个作为收起View，否则最后一个View不显示
                if (line > mFoldLine) {
                    child.layout(childLeft, childTop, childLeft + childWidth - mTagMarginRight,
                            childTop + childHeight - mTagMarginBottom);
                    convertUpView(child);
                }
            } else {
                child.layout(childLeft, childTop, childLeft + childWidth - mTagMarginRight,
                        childTop + childHeight - mTagMarginBottom);
            }
        }
    }

    private void reMeasureChild(View child, int width, int height) {
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width - mFoldViewWidth - mTagMarginRight
                , MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height
                , MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private void measureFoldView(View child, int height) {
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mFoldViewWidth
                , MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height
                , MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 设置折叠状态
     *
     * @param fold
     */
    public void setFold(boolean fold) {
        this.mNeedFold = fold;
        reloadData();
    }

    /**
     * 处理折叠view
     * @param foldView
     */
    private void convertFoldView(View foldView) {
        if (mTagAdapter == null) {
            return;
        }
        mTagAdapter.onConvertFoldView(foldView);
        foldView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFold(false);
            }
        });
    }

    /**
     * 处理收起view
     * @param foldView
     */
    private void convertUpView(View foldView) {
        if (mTagAdapter == null) {
            return;
        }
        mTagAdapter.onConvertUpView(foldView);
        foldView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFold(true);
            }
        });
    }

    /**
     * 设置tag适配器
     *
     * @param adapter
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
        int count = mTagAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View childView = mTagAdapter.getView(i, null, this);
            addView(childView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        // 如果数据不为空，添加一个数据作为收起View使用
        if (count > 0) {
            View upView = mTagAdapter.getView(0, null, this);
            addView(upView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }

}
