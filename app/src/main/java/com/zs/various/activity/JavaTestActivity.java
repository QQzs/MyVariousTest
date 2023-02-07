package com.zs.various.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.bean.TestBean;
import com.zs.various.bean.java1.SubClass;
import com.zs.various.bean.java2.Man;
import com.zs.various.bean.java2.Person;
import com.zs.various.util.Constant;
import com.zs.various.util.DateUtils;
import com.zs.various.util.LogUtil;
import com.zs.various.view.SearchEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

/**
 * Created by zs
 * Date：2019年 02月 13日
 * Time：13:46
 * —————————————————————————————————————
 * About: 测试
 * —————————————————————————————————————
 */
public class JavaTestActivity extends BaseActivity{

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

        int[] data = {1,2,3,4};
        Constant.changeData(data);
        LogUtil.Companion.logShow(Arrays.toString(data));

        findViewById(R.id.tv_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        printData1();
        printData2();

        initEdit();
    }

    private void initEdit() {

        SearchEditText edit_text = findViewById(R.id.edit_text);
        edit_text.setSearchEditListener(new SearchEditText.SearchEditListener() {

            @Override
            public void onTextChanged(String text) {

            }

            @Override
            public void startSearchAction(String text) {
                LogUtil.logShow("text = " + text);
            }
        });

    }

    private void printData1(){
        Person person = new Man();
        LogUtil.Companion.logShow(person.name);
        person.action();

        /** 输出结果：
         * Person constructor
          Man constructor
          Person
          Man20
          print Person
         */

        /**
         * 注意
         * 1、父类的构造器调用以及初始化过程一定在子类的前面
         * 2、覆盖只只对于非静态方法，而隐藏是针对于成员变量和静态方法
         */

    }

    private void printData2(){
        new SubClass();
        /**
         * a
         * d
         * b
         * c
         * e
         * f
         */

        /**
         * 1、静态代码块最先打印
         * 2、父类代码块 -》父类构造方法 -》子类代码块 -》子类构造方法
         */

    }


}
