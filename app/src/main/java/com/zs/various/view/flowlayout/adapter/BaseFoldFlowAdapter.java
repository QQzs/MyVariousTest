package com.zs.various.view.flowlayout.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;

import com.zs.various.view.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局adapter
 * Created by zhangkun on 2018/3/27.
 */

public abstract class BaseFoldFlowAdapter<T> extends BaseAdapter {

    protected ViewGroup mParent;

    protected List<T> mDatas;

    /**
     * 是否实心展示
     */
    protected boolean isSolidTag = true;

    public BaseFoldFlowAdapter() {
        mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas == null ? null : mDatas.get(i);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //不存在复用情况则不选择view holder模式,convertView也没有意义
        final View tag = View.inflate(parent.getContext(), getItemViewType(position), null);
        onConvertView(position, getItemViewType(position), tag, mDatas.get(position));
        return tag;
    }

    public ViewGroup getFlowTagLayout() {
        return mParent;
    }

    //分页时添加数据
    public void addAll(List<T> previewList) {
        mDatas.clear();
        if (previewList != null) {
            mDatas.addAll(previewList);
            notifyDataSetChanged();
        }
    }

    public void addItem(T t) {
        if (t == null) {
            return;
        }
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void addItem(T t, int position) {
        if (t == null) {
            return;
        }
        mDatas.add(position, t);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas;
    }

    public void removeItemByPosition(int position) {
        if (position > mDatas.size() - 1 || position < 0) {
            return;
        }
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    // 清空列表
    public void clearAll() {
        addAll(new ArrayList<T>());
        notifyDataSetChanged();
    }

    /**
     * 不重新创建item view的前提下，刷新item view的数据显示
     * @param position 要刷新的view的位置
     */
    public void notifyView(int position){
        if (mParent!=null){
            onConvertView(position,getTagLayoutId(position),mParent.getChildAt(position),mDatas.get(position));
        }
    }

    /**
     * 不重新创建item view的前提下，刷新所有item view的数据显示
     */
    public void notifyAllViews(){
        if (mParent!=null){
            for (int i = 0; i < mParent.getChildCount(); i++) {
                notifyView(i);
            }
        }
    }

    /**
     * 设置折叠Vie
     * @param view 折叠View
     */
    public void onConvertFoldView(View view) {

    }

    @Override
    public int getItemViewType(int position) {
        return getTagLayoutId(position);
    }

    public void onAttachedToParent(ViewGroup flowTagLayout) {
        mParent = flowTagLayout;
    }

    public void onDetachedFromParent(ViewGroup flowTagLayout){

    }

    public abstract void onConvertView(int position, @LayoutRes int tagLayoutId, View tag, T t);

    public abstract @LayoutRes
    int getTagLayoutId(int position);

    public void setIsSolidTag(boolean isSolidTag){
        this.isSolidTag = isSolidTag;
    }

}
