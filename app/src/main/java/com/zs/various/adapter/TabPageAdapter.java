package com.zs.various.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zs.various.fragment.PageFragmentTest;

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

    private List<String> mTitles = new ArrayList<>();

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabPageAdapter(FragmentManager fm, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragmentTest.Companion.getInstance(position);
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
