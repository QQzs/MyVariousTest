package com.zs.various.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zs.various.R;
import com.zs.various.bean.TitleData;
import com.zs.various.util.LogUtil;

import java.util.List;

/**
 * @Author: zs
 * @Date: 2020/12/27 10:51 AM
 * @Description:
 */
public class TabPageAdapter2 extends RecyclerView.Adapter<TabPageAdapter2.TabViewHolder> {

    private List<TitleData> dataList;

    public TabPageAdapter2(List<TitleData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tab_layout, parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.tv_title);
        title.setText(dataList.get(position).getTitle());
        LogUtil.logShow("position = " + position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class TabViewHolder extends RecyclerView.ViewHolder{

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
