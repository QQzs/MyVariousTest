package com.zs.various.view.flowlayout.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zs.various.R;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/26 12:05 PM
 * @Description:
 */
public class FoldAdapter extends BaseFoldFlowAdapter<String> {

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
    public void onConvertFoldView(View view , boolean isFold) {
        super.onConvertFoldView(view , isFold);
        TextView tv = view.findViewById(R.id.tv_tag_view);
        tv.setVisibility(View.GONE);
        ImageView arrow = view.findViewById(R.id.iv_tag_arrow);
        arrow.setVisibility(View.VISIBLE);
        arrow.setImageResource(isFold ? R.mipmap.icon_fold_arrow_down : R.mipmap.icon_fold_arrow_up);
    }

//    @Override
//    public void onConvertUpView(View view) {
//        super.onConvertUpView(view);
//        TextView tv = view.findViewById(R.id.tv_tag_view);
//        tv.setVisibility(View.GONE);
//        ImageView arrow = view.findViewById(R.id.iv_tag_arrow);
//        arrow.setImageResource(R.mipmap.icon_fold_arrow_up);
//        arrow.setVisibility(View.VISIBLE);
//    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
