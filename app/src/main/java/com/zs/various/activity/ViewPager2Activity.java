package com.zs.various.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zs.various.R;
import com.zs.various.adapter.TabPageAdapter2;
import com.zs.various.adapter.TabPageAdapter3;
import com.zs.various.base.BaseActivity;
import com.zs.various.bean.TitleData;
import com.zs.various.fragment.PageFragment;
import com.zs.various.fragment.PageFragmentTest;
import com.zs.various.fragment.TabFragment;
import com.zs.various.listener.TabFragmentHelper;
import com.zs.various.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zs
 * @Date: 2020/12/27 10:34 AM
 * @Description:
 */
public class ViewPager2Activity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager2 topViewPager;
    private ViewPager2 bottomViewPager;

    private TabPageAdapter2 adapter2;
    private TabPageAdapter3 adapter3;

    private List<TitleData> titles;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        topViewPager = findViewById(R.id.view_pager_top);
        bottomViewPager = findViewById(R.id.view_pager_bottom);
    }

    @Override
    public void initData() {
        titles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            titles.add(new TitleData("title" + i, i));
        }

        adapter2 = new TabPageAdapter2(titles);
        bottomViewPager.setAdapter(adapter2);
        bottomViewPager.setOffscreenPageLimit(1);

        adapter3 = new TabPageAdapter3(this, titles, new TabFragmentHelper() {
            @Override
            public Fragment getFragment(int index) {
                LogUtil.logShow("getFragment index = " + index);
                return TabFragment.getInstance(index);
            }
        });

        topViewPager.setAdapter(adapter3);
        topViewPager.setOffscreenPageLimit(1);
        topViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, topViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position).getTitle());
            }
        });
        tabLayoutMediator.attach();


    }
}
