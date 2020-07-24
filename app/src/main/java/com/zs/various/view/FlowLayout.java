package com.zs.various.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zs.various.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/23 2:42 PM
 * @Description:
 */
public class FlowLayout extends ViewGroup {

    private int mMaxLine = 2;

    private int mTagMarginRight = 20;
    private int mTagMarginBottom = 10;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //遍历去调用所有子元素的measure方法（child.getMeasuredHeight()才能获取到值，否则为0）
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = 0, measuredHeight = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //由于计算子view所占宽度，这里传值需要自身减去PaddingRight宽度，PaddingLeft会在接下来计算子元素位置时加上
        int viewHeight = compute(widthSize - getPaddingLeft() - getPaddingRight());

        //EXACTLY模式：对应于给定大小或者match_parent情况
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
            //AT_MOS模式：对应wrap-content（需要手动计算大小，否则相当于match_parent）
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = viewHeight;
        }
        //设置flow的宽高
        setMeasuredDimension(measuredWidth, measuredHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = (Rect) getChildAt(i).getTag();
            if (rect != null) {
                child.layout(rect.left, rect.top, rect.right, rect.bottom);
            }
        }

    }

    /**
     * 测量过程
     *
     * @param flowWidth 该view的宽度
     * @return 返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
     */
    private int compute(int flowWidth) {
        int rowsWidth = getPaddingLeft();//当前行已占宽度(注意需要加上paddingLeft)
        int columnHeight = getPaddingTop();//当前行顶部已占高度(注意需要加上paddingTop)
        int rowsMaxHeight = 0;//当前行所有子元素的最大高度（用于换行累加高度）
        int childWidth = 0;
        int childHeight = 0;
        int childLeft = 0;
        int childTop = 0;
        int line = 0;
        int lineCount = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //获取元素测量宽度和高度
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();

            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            childWidth = measuredWidth + mTagMarginRight;
            childHeight = measuredHeight + mTagMarginBottom;
            //判断是否换行： 该行已占大小+该元素大小>父容器宽度  则换行
            rowsMaxHeight = Math.max(rowsMaxHeight, childHeight);

//            if (childWidth > flowWidth) {
//                child.getLayoutParams().width = flowWidth;
//                measureChild(child , getMeasuredWidth() , getMeasuredHeight());
//                childWidth = child.getMeasuredWidth();
//            }

            //换行
            if (rowsWidth + childWidth > flowWidth) {
                line ++;
//                if (line >= mMaxLine) {
//                    break;
//                }
                // 重置行宽度
                rowsWidth = getPaddingLeft();
                childLeft = getPaddingLeft();
                // 累加上该行子元素最大高度
                if (lineCount == 0) {
                    childTop = columnHeight;
                } else {
                    childTop = columnHeight + childHeight;
                }
                columnHeight += childHeight;
                lineCount = 0;
            } else {
                childLeft = rowsWidth;
                childTop = columnHeight;
                rowsWidth += childWidth;
                lineCount ++;
            }
            //累加上该行子元素宽度
//            rowsWidth += childWidth;
            //判断时占的宽段时加上margin计算，设置顶点位置时不包括margin位置，不然margin会不起作用，这是给View设置tag,在onlayout给子元素设置位置再遍历取出
            int right = childLeft + childWidth - mTagMarginRight;
            if (lineCount == 0) {
                right = Math.min(flowWidth - 200 , right);

            }
            child.setTag(new Rect(childLeft , childTop , right, childTop + childHeight - mTagMarginBottom));

        }
        return line * childHeight;
    }

    public void updateData(List<String> list) {
        removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(list.get(i));
            tv.setBackgroundResource(R.drawable.bg_tag);
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setPadding(20, 10, 20, 10);
            addView(tv);
        }
    }
}
