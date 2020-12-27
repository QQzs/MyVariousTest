package com.zs.various.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zs.various.listener.TabFragmentHelper;

/**
 * @Author: zs
 * @Date: 2020/12/27 11:30 AM
 * @Description:
 */
public class TabPageAdapter3 extends FragmentStateAdapter {

    private TabFragmentHelper fragmentHelper;

    public TabPageAdapter3(@NonNull FragmentActivity fragmentActivity, TabFragmentHelper helper) {
        super(fragmentActivity);
        fragmentHelper = helper;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentHelper.getFragment(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
