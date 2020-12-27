package com.zs.various.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.zs.various.fragment.PageFragmentTest;
import com.zs.various.listener.TabFragmentHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：11:19
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TabPageAdapter extends FragmentStatePagerAdapter {

    // FragmentStatePagerAdapter  FragmentPagerAdapter

    private List<String> mTitles;
    private TabFragmentHelper fragmentHelper;

    public TabPageAdapter(FragmentManager fm, List<String> mTitles, TabFragmentHelper fragmentHelper) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentHelper = fragmentHelper;
    }

    @Override
    public Fragment getItem(int position) {
//        return PageFragmentTest.Companion.getInstance(position);
        return fragmentHelper.getFragment(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
}
