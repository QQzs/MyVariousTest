package com.zs.various.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zs.various.bean.TitleData;
import com.zs.various.listener.TabFragmentHelper;

import java.util.List;

/**
 * @Author: zs
 * @Date: 2020/12/27 11:30 AM
 * @Description:
 */
public class TabPageAdapter3 extends FragmentStateAdapter {

    private List<TitleData> titles;
    private TabFragmentHelper fragmentHelper;

    public TabPageAdapter3(@NonNull FragmentActivity fragmentActivity, List<TitleData> titles, TabFragmentHelper helper) {
        super(fragmentActivity);
        this.titles = titles;
        fragmentHelper = helper;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentHelper.getFragment(position);
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }
}
