package com.zs.various.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zs
 * Date：2017年 09月 25日
 * Time：11:24
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setLayoutId() != 0){
            initContentView(setLayoutId());
        }
    }

    protected void initContentView(int layoutResID){
        setContentView(layoutResID);
        mActivity = this;
        /**
         * 初始化一些UI
         */
        initView();

        /**
         * 初始化一些数据
         */
        initData();
    }

    /**
     *
     * @return 返回布局
     */
    protected abstract int setLayoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onClick(View v) {

    }
}
