package com.zs.various.activity;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.bean.TestBean;
import com.zs.various.util.LogUtil;

import java.util.HashSet;

/**
 * Created by zs
 * Date：2019年 02月 13日
 * Time：13:46
 * —————————————————————————————————————
 * About: 测试
 * —————————————————————————————————————
 */
public class TestActivity extends BaseActivity{

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        HashSet<TestBean> hashSet = new HashSet<>();
        TestBean bean1 = new TestBean("aaa" , 1);
        TestBean bean2 = new TestBean("aaa" , 1);
        hashSet.add(bean1);
        hashSet.add(bean2);
        bean1.setAge(2);
        hashSet.add(bean1);
        LogUtil.Companion.logShow("size = " + hashSet.size());

    }

}
