package com.zs.various.activity;

import android.util.Log;
import android.view.View;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.bean.TestBean;
import com.zs.various.bean.java1.SubClass;
import com.zs.various.bean.java2.Man;
import com.zs.various.bean.java2.Person;
import com.zs.various.util.Constant;
import com.zs.various.util.DateUtils;
import com.zs.various.util.LogUtil;

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

        findViewById(R.id.tv_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time1 = getZeroTime(getThisWeekMonday(new Date()).getTime());
                long time2 = getZeroTime(getNextWeekMonday(new Date()).getTime());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Log.d("My_Log" , "time1 = " + time1 + "  " + DateUtils.parseDateTime(time1));
                Log.d("My_Log" , "time2 = " + time2 + "  " + DateUtils.parseDateTime(time2));
            }
        });

    }


    private void getData(){

        Person person = new Man();
        LogUtil.Companion.logShow(person.name);
        person.action(10);

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

    /**
     * 获取一天中的零点零分零秒时间戳
     * @return
     */
    public static long getZeroTime(long current){
        return current / (1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
    }

    /**
     * 获取这周一Date
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取下周一Date
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

}
