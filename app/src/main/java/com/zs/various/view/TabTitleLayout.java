package com.zs.various.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zs.various.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 14日
 * Time：9:12
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TabTitleLayout extends LinearLayout {

    private OnTabClickListener mItemListener;
    private List<String> mTabData = new ArrayList();
    private int mCurrentIndex = 0;

    public TabTitleLayout(Context context) {
        super(context);
        initView();
    }

    public TabTitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabTitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 设置数据
     * @param data
     */
    public void initData(List<String> data){
        this.mTabData = data;
        initView();
    }

    /**
     * 设置选中item
     * @param position
     */
    public void setSelectItem(int position){
        this.mCurrentIndex = position;
        initView();
    }

    /**
     * 添加Tab
     */
    private void initView(){
        removeAllViews();
        for (int i = 0 ; i< mTabData.size() ; i++){
            View tabView = View.inflate(getContext(), R.layout.tab_item_view,null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0 , LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            tabView.setLayoutParams(layoutParams);
            setTabStatus(tabView , i);
            final int index = i;
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = index;
                    for (int i = 0 ; i< getChildCount() ; i++){
                        setTabStatus(getChildAt(i) , i);
                    }
                    if (mItemListener != null){
                        mItemListener.backClick(mCurrentIndex);
                    }
                }
            });
            addView(tabView);
        }

    }

    /**
     * 设置View
     * @param view
     * @param position
     */
    private void setTabStatus(View view , int position){
        TextView title = view.findViewById(R.id.tv_tab_title);
        View line = view.findViewById(R.id.view_line);
        title.setText(mTabData.get(position));
        if (position == mCurrentIndex){
            title.setTextColor(Color.parseColor("#288dde"));
            line.setVisibility(View.VISIBLE);
        }else{
            title.setTextColor(Color.parseColor("#666666"));
            line.setVisibility(View.GONE);
        }
    }

    public void setItemListener(OnTabClickListener listener){
        this.mItemListener = listener;
    }

    public interface OnTabClickListener{
        void backClick(int position);
    }

}
