package com.zs.various.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：13:39
 * —————————————————————————————————————
 * About: 懒加载
 * —————————————————————————————————————
 */
public abstract class LazyFragment extends Fragment {

    /**
     * 创建根布局 RootView，默认布局添加到根布局中
     *
     * setUserVisibleHint 在 onCreateView方法之前调用
     * 正在显示 | 没有被加载过 | 已经调用过onCreateView方法 三个条件才进行加载布局，把布局添加到根布局中
     *
     */

    // Fragment的根View
    protected FrameLayout mRootView;

    private boolean hasLoaded = false;  //标识是否已经加载过
    private boolean hasCreated = false; //标识onCreateView是否已调用
    private boolean needInit = false;   //标识是否需要在onCreateView中

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("lazy_load", "LazyFragment onCreateView. ");
        final Context context = inflater.getContext();
        mRootView = new FrameLayout(context);
        if(needInit) {  // 第一个fragment 在 setUserVisibleHint中 needInit 置为true，
            initWrapper();
            needInit = false;
        }
        hasCreated = true;
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("lazy_load", "LazyFragment setUserVisibleHint "+isVisibleToUser);
        if (isVisibleToUser && !hasLoaded) {    // 如果当前Fragment向用户展示且没有加载过，进行下一步判断
            if (hasCreated) {   // 如果onCreateView已经被创建，此时进行加载
                initWrapper();
            } else {            // 如果Fragment没有创建，那么设置标记，在onCreateView中加载（第一个fragment）
                needInit = true;
            }
        }
    }

    /**
     * 此函数开始数据加载的操作，且仅调用一次
     */
    private void initWrapper() {
        LayoutInflater.from(getContext()).inflate(setContentView(), mRootView);
        initView();
        hasLoaded = true;
    }

    /**
     * 供子类使用，子类fragment初始化操作，此函数内部真正开始进行页面的一些列操作
     */
    abstract void initView();

    /**
     * 子类fragment设置布局，返回fragment要设定的布局
     * @return 子类fragment要显示的布局
     */
    @LayoutRes
    abstract int setContentView();


    @SuppressWarnings("unchecked")
    @CheckResult
    public <T extends View> T getView(int id) {
        return (T) mRootView.findViewById(id);
    }

}
