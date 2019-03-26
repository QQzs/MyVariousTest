package com.zs.various.activity;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.bean.TestBean;
import com.zs.various.bean.java1.SubClass;
import com.zs.various.bean.java2.Man;
import com.zs.various.bean.java2.Person;
import com.zs.various.util.Constant;
import com.zs.various.util.LogUtil;

import java.util.Arrays;
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
        getData();

        new SubClass();

        int[] data = {1,2,3,4};
        Constant.changeData(data);
        LogUtil.Companion.logShow(Arrays.toString(data));

    }


    private void getData(){

        Person person = new Man();
        LogUtil.Companion.logShow(person.name);
        person.action(10);
        person.print();

        /** 输出结果：
         * Person constructor
          Man constructor
          Person
          Man10
          print Person
         */

        /**
         * 注意
         * 1、父类的构造器调用以及初始化过程一定在子类的前面
         * 2、覆盖只只对于非静态方法，而隐藏是针对于成员变量和静态方法
         */

    }


}
