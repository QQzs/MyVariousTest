package com.zs.various.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zs.various.R;
import com.zs.various.base.BaseFragment;
import com.zs.various.util.LogUtil;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/6/21 4:10 PM
 * @Description:
 */
public class JavaFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.item_mesage;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        View flipper = view.findViewById(R.id.message_view_flipper);

        flipper.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getContext() == null){
                    LogUtil.logShow("JavaFragment null");
                }
                if (getContext() instanceof Activity){
                    if (((Activity) getContext()).isDestroyed()){
                        LogUtil.logShow("JavaFragment isDestroyed");
                    }

                    if (((Activity) getContext()).isFinishing()){
                        LogUtil.logShow("JavaFragment isFinishing");
                    }
                }
            }
        } , 3000);
    }

    @Override
    protected void initData() {

    }
}
