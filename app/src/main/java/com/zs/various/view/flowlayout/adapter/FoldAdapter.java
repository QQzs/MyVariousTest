package com.zs.various.view.flowlayout.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zs.various.R;
import com.zs.various.view.flowlayout.FlowTagLayout;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/26 12:05 PM
 * @Description:
 */
public class FoldAdapter extends BaseFlowTagAdapter<String> {

    @Override
    public void onConvertView(int position, int tagLayoutId, View tag, String s) {
        TextView tv = tag.findViewById(R.id.tv_tag_view);
        tv.setText(s);
    }

    @Override
    public int getTagLayoutId(int position) {
        return R.layout.item_tag_view;
    }

    @Override
    public void onAttachedToParent(ViewGroup flowTagLayout) {
        super.onAttachedToParent(flowTagLayout);
        //绑定adapter的时候刷新下
        this.notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }
}
