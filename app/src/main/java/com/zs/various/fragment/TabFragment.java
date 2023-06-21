package com.zs.various.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.zs.various.R;
import com.zs.various.base.BaseFragment;
import com.zs.various.util.LogUtil;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/6/21 4:10 PM
 * @Description:
 */
public class TabFragment extends BaseFragment {

    private int index;

    public static TabFragment getInstance(int index){
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_tab_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        index = getArguments().getInt("index");
        TextView title = view.findViewById(R.id.tv_title);
        title.setText("Title " + index);

        String imageUrl = "https://img.hongrenshuo.com.cn/h5/a840bde806813f8ae20725eb0276422c.png";
        ImageView image = view.findViewById(R.id.image_view_url);
        Glide.with(this).load(imageUrl).into(image);
    }

    @Override
    protected void initData() {
        LogUtil.logShow("index = " + index);
    }
}
