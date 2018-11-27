package com.zs.various.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zs.various.R;

/**
 * Created by zs
 * Date：2018年 11月 26日
 * Time：16:30
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ItemTabView extends FrameLayout {

    public ItemTabView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ItemTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//
//
//    }

    private void initView(Context context){

        LayoutInflater.from(context).inflate(R.layout.item_tab_view , this);



    }


}
